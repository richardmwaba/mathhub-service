package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Invoice;
import com.hubformath.mathhubservice.model.ops.cashbook.InvoiceRequest;
import com.hubformath.mathhubservice.model.ops.cashbook.InvoiceStatus;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.repository.ops.cashbook.InvoiceRepository;
import com.hubformath.mathhubservice.service.sis.StudentService;
import com.hubformath.mathhubservice.service.systemconfig.UsersService;
import com.hubformath.mathhubservice.util.StudentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final StudentService studentService;

    private final UsersService usersService;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository,
                          StudentService studentService,
                          UsersService usersService) {
        this.invoiceRepository = invoiceRepository;
        this.studentService = studentService;
        this.usersService = usersService;
    }

    public List<Invoice> getAllInvoices(List<String> studentId, List<InvoiceStatus> invoiceStatus) {
        return invoiceRepository.findAll()
                                .parallelStream()
                                .filter(invoice -> shouldGetDefaultInvoices(invoiceStatus,
                                                                            invoice) || invoiceStatus.contains(invoice.getStatus()))
                                .filter(invoice -> {
                                    String invoiceStudentId = invoice.getStudent().getId();
                                    return CollectionUtils.isEmpty(studentId) || studentId.contains(invoiceStudentId);
                                }).toList();
    }

    private boolean shouldGetDefaultInvoices(List<InvoiceStatus> invoiceStatus, Invoice invoice) {
        return CollectionUtils.isEmpty(invoiceStatus ) && isPendingOrOverdue(invoice);
    }

    private static boolean isPendingOrOverdue(Invoice invoice) {
        return invoice.getStatus().equals(InvoiceStatus.PENDING) || invoice.getStatus()
                                                                           .equals(InvoiceStatus.OVERDUE);
    }

    public Invoice getInvoiceById(String invoiceId) {
        return invoiceRepository.findById(invoiceId).orElseThrow();
    }

    public Invoice createInvoice(InvoiceRequest invoiceRequest) {
        final String studentId = invoiceRequest.studentId();
        final Student student = studentService.getStudentById(studentId);
        final Invoice existingInvoice = invoiceRepository.findByStudent(student).orElse(null);

        if (existingInvoice != null && existingInvoice.getDueDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("An active invoice already exists for this student");
        }

        final Double amount = StudentUtil.computeStudentFinancialSummary(student).getTotalAmountOwing();
        final String narration = invoiceRequest.narration();
        final Invoice newInvoice = new Invoice(student,
                                               amount,
                                               narration,
                                               InvoiceStatus.PENDING,
                                               usersService.getLoggedInUser());

        return invoiceRepository.save(newInvoice);
    }

    public Invoice updateInvoice(String invoiceId, InvoiceRequest invoiceRequest) {
        Invoice updatedInvoice = invoiceRepository.findById(invoiceId)
                                                  .map(invoice -> {
                                                      Optional.ofNullable(invoiceRequest.narration())
                                                              .ifPresent(invoice::setNarration);
                                                      Optional.ofNullable(invoiceRequest.amount())
                                                              .ifPresent(invoice::setAmount);
                                                      Optional.ofNullable(invoiceRequest.invoiceStatus())
                                                              .ifPresent(invoice::setStatus);
                                                      Optional.ofNullable(invoiceRequest.studentId())
                                                              .ifPresent(studentId -> {
                                                                  Student student = studentService.getStudentById(
                                                                          studentId);
                                                                  invoice.setStudent(student);
                                                              });
                                                      return invoice;
                                                  })
                                                  .orElseThrow();

        return invoiceRepository.save(updatedInvoice);
    }

    public void deleteInvoice(String invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                                           .orElseThrow();

        invoiceRepository.delete(invoice);
    }
}
