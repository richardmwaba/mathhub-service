package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.dto.ops.cashbook.TuitionPaymentDto;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransactionType;
import com.hubformath.mathhubservice.model.ops.cashbook.Invoice;
import com.hubformath.mathhubservice.model.ops.cashbook.InvoiceStatus;
import com.hubformath.mathhubservice.model.ops.cashbook.PaymentStatus;
import com.hubformath.mathhubservice.model.ops.cashbook.Receipt;
import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.TuitionPaymentRepository;
import com.hubformath.mathhubservice.service.sis.StudentService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class TuitionPaymentService {

    private final TuitionPaymentRepository tuitionPaymentRepository;

    private final PaymentMethodService paymentMethodService;

    private final StudentService studentService;

    private final InvoiceService invoiceService;

    @Autowired
    public TuitionPaymentService(final TuitionPaymentRepository tuitionPaymentRepository,
                                 final PaymentMethodService paymentMethodService,
                                 final StudentService studentService,
                                 final InvoiceService invoiceService) {
        this.tuitionPaymentRepository = tuitionPaymentRepository;
        this.paymentMethodService = paymentMethodService;
        this.studentService = studentService;
        this.invoiceService = invoiceService;
    }

    public List<TuitionPayment> getAllTuitionPayments() {
        return tuitionPaymentRepository.findAll();
    }

    public TuitionPayment getTuitionPaymentById(Long id) {
        return tuitionPaymentRepository.findById(id).orElseThrow();
    }

    @Transactional
    public TuitionPayment createTuitionPayment(TuitionPaymentDto tuitionPayment) {
        final Student student = studentService.getStudentById(tuitionPayment.getStudentId());
        if(!student.isOwingPayment()) {
            return  null;
        }

        final Long invoiceId = tuitionPayment.getInvoiceId();
        final Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        final Long paymentMethodId = tuitionPayment.getPaymentMethodId();
        final String narration = tuitionPayment.getNarration();
        final Double amount = tuitionPayment.getAmount();
        final Set<Long> lessonsIds = tuitionPayment.getLessonsIds();

        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);

        student.getLessons()
                .stream()
                .filter(lesson -> lesson.getLessonPaymentStatus().equals(PaymentStatus.UNPAID) && lessonsIds.contains(lesson.getId()))
                .forEach(lesson -> lesson.setLessonPaymentStatus(PaymentStatus.PAID));

        final CashTransaction newCashTransaction = new CashTransaction(paymentMethod, CashTransactionType.CASH_IN, narration, amount);
        final Receipt receipt = new Receipt(newCashTransaction.getTransactionNumber());

        // Update student financial summary
        student.setStudentFinancialSummary(studentService.computeStudentFinancialSummary(student));

        // Update invoice
        invoice.setInvoiceStatus(InvoiceStatus.PAID);
        invoiceService.updateInvoice(invoiceId, invoice);

        final TuitionPayment newTuitionPayment = new TuitionPayment(newCashTransaction, student, paymentMethod, amount,  invoice.getId(), receipt, narration);

        return tuitionPaymentRepository.save(newTuitionPayment);
    }
}