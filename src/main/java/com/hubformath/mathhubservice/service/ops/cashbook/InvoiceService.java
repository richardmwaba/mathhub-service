package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.dto.ops.cashbook.InvoiceDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Invoice;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.repository.ops.cashbook.InvoiceRepository;
import com.hubformath.mathhubservice.service.sis.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    
    private final InvoiceRepository invoiceRepository;

    private final StudentService studentService;
    
    @Autowired
    public InvoiceService(final InvoiceRepository invoiceRepository, final StudentService studentService) {
        this.invoiceRepository = invoiceRepository;
        this.studentService = studentService;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElseThrow();
    }

    public Invoice createInvoice(InvoiceDto invoiceRequest) {
        final Long studentId = invoiceRequest.getStudentId();
        final String narration = invoiceRequest.getNarration();

        final Student student = studentService.getStudentById(studentId);
        final Double amount = studentService.computeStudentFinancialSummary(student).getAmountOwing();
        final Invoice newInvoice = new Invoice(amount, narration);

        return invoiceRepository.save(newInvoice);
    }

    public Invoice updateInvoice(Long id, Invoice invoiceRequest) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    Optional.ofNullable(invoiceRequest.getNarration()).ifPresent(invoice::setNarration);
                    Optional.ofNullable(invoiceRequest.getAmount()).ifPresent(invoice::setAmount);
                    Optional.ofNullable(invoiceRequest.getInvoiceStatus()).ifPresent(invoice::setInvoiceStatus);
                    Optional.ofNullable(invoiceRequest.getDueDate()).ifPresent(invoice::setDueDate);
                    return invoiceRepository.save(invoice);
                })
                .orElseThrow();
    }

    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow();

        invoiceRepository.delete(invoice);
    }
}
