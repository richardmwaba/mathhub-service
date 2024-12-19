package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.model.auth.User;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransactionType;
import com.hubformath.mathhubservice.model.ops.cashbook.Invoice;
import com.hubformath.mathhubservice.model.ops.cashbook.InvoiceRequest;
import com.hubformath.mathhubservice.model.ops.cashbook.InvoiceStatus;
import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.ops.cashbook.Receipt;
import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;
import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPaymentRequest;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.TuitionPaymentRepository;
import com.hubformath.mathhubservice.service.sis.StudentService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import com.hubformath.mathhubservice.service.systemconfig.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TuitionPaymentService {

    private final TuitionPaymentRepository tuitionPaymentRepository;

    private final PaymentMethodService paymentMethodService;

    private final StudentService studentService;

    private final InvoiceService invoiceService;

    private final UsersService userService;

    @Autowired
    public TuitionPaymentService(TuitionPaymentRepository tuitionPaymentRepository,
                                 PaymentMethodService paymentMethodService,
                                 StudentService studentService,
                                 InvoiceService invoiceService,
                                 UsersService userService) {
        this.tuitionPaymentRepository = tuitionPaymentRepository;
        this.paymentMethodService = paymentMethodService;
        this.studentService = studentService;
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    public List<TuitionPayment> getAllTuitionPayments() {
        return tuitionPaymentRepository.findAll();
    }

    public TuitionPayment getTuitionPaymentById(String tuitionPaymentId) {
        return tuitionPaymentRepository.findById(tuitionPaymentId).orElseThrow();
    }

    @Transactional
    public TuitionPayment createTuitionPayment(TuitionPaymentRequest tuitionPaymentRequest) {
        final Student student = studentService.getStudentById(tuitionPaymentRequest.studentId());
        if (!student.getFinancialSummary().isOwing()) {
            return null;
        }

        User currentUser = userService.getLoggedInUser();
        final Invoice invoice = invoiceService.getInvoiceById(tuitionPaymentRequest.invoiceId());
        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(tuitionPaymentRequest.paymentMethodId());
        final String narration = tuitionPaymentRequest.narration();
        final Double amount = tuitionPaymentRequest.amount();
        final Set<String> lessonsIds = tuitionPaymentRequest.lessonsIds();
        final String transactionNumber = UUID.randomUUID().toString().toUpperCase().replace("-", "");

        // Update student's classes payment status
        student.getEnrolledClasses()
               .parallelStream()
               .filter(lesson -> lesson.getPaymentStatus().equals(PaymentStatus.UNPAID) && lessonsIds.contains(
                       lesson.getId()))
               .forEach(lesson -> lesson.setPaymentStatus(PaymentStatus.PAID));

        final CashTransaction newCashTransaction = new CashTransaction(transactionNumber,
                                                                       paymentMethod,
                                                                       CashTransactionType.CASH_IN,
                                                                       narration,
                                                                       amount,
                                                                       currentUser);
        final Receipt receipt = new Receipt(newCashTransaction.getTransactionNumber(), currentUser);

        // Update student financial summary
        student.setFinancialSummary(studentService.computeStudentFinancialSummary(student));

        // Update invoice
        InvoiceRequest invoiceRequest = new InvoiceRequest(null, null, narration, InvoiceStatus.PAID);
        invoiceService.updateInvoice(invoice.getId(), invoiceRequest);

        final TuitionPayment newTuitionPayment = new TuitionPayment(newCashTransaction,
                                                                    student,
                                                                    paymentMethod,
                                                                    amount,
                                                                    narration);
        newTuitionPayment.setInvoice(invoice);
        newTuitionPayment.setReceipt(receipt);
        newTuitionPayment.setCreatedBy(currentUser);

        return tuitionPaymentRepository.save(newTuitionPayment);
    }
}