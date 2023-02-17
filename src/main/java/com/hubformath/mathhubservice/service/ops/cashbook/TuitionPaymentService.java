package com.hubformath.mathhubservice.service.ops.cashbook;

import com.hubformath.mathhubservice.dto.ops.cashbook.TuitionPaymentRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransactionType;
import com.hubformath.mathhubservice.model.ops.cashbook.Receipt;
import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;
import com.hubformath.mathhubservice.model.sis.Lessons;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.repository.ops.cashbook.TuitionPaymentRepository;
import com.hubformath.mathhubservice.service.sis.ILessonsService;
import com.hubformath.mathhubservice.service.sis.IStudentService;
import com.hubformath.mathhubservice.service.systemconfig.ICashTransactionCategoryService;
import com.hubformath.mathhubservice.service.systemconfig.IPaymentMethodService;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TuitionPaymentService {

    private final TuitionPaymentRepository tuitionPaymentRepository;

    private final String notFoundItemName;

    private final IPaymentMethodService paymentMethodService;

    private final ILessonsService lessonsService;

    private final ICashTransactionCategoryService cashTransactionCategoryService;

    private final IStudentService studentService;

    @Autowired
    public TuitionPaymentService(final IPaymentMethodService paymentMethodService,
                                     final ILessonsService lessonsService,
                                     final ICashTransactionCategoryService cashTransactionCategoryService,
                                     final IStudentService studentService,
                                     final TuitionPaymentRepository tuitionPaymentRepository) {
        super();
        this.paymentMethodService = paymentMethodService;
        this.lessonsService = lessonsService;
        this.cashTransactionCategoryService = cashTransactionCategoryService;
        this.studentService = studentService;
        this.tuitionPaymentRepository = tuitionPaymentRepository;
        this.notFoundItemName = "tuition payment";
    }

    public List<TuitionPayment> getAllTuitionPayments() {
        return tuitionPaymentRepository.findAll();
    }

    public TuitionPayment getTuitionPaymentById(Long id) {
        Optional<TuitionPayment> tuitionPayment = tuitionPaymentRepository.findById(id);

        if (tuitionPayment.isPresent()) {
            return tuitionPayment.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Transactional
    public TuitionPayment createTuitionPayment(TuitionPaymentRequestDto tuitionPaymentRequest) {
        final Long paymentMethodId = tuitionPaymentRequest.getPaymentMethodId();
        final Long studentId = tuitionPaymentRequest.getStudentId();
        final Long lessonsId = tuitionPaymentRequest.getLessonsId();
        final Long transactionCategoryId = tuitionPaymentRequest.getTransactionCategoryId();
        final String narration = tuitionPaymentRequest.getNarration();
        final Double amount = tuitionPaymentRequest.getAmount();
        final LocalDate paymentDate = LocalDate.now();

        final Lessons lessons = lessonsService.getLessonsById(lessonsId);
        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
        final Student student = studentService.getStudentById(studentId);
        final CashTransactionCategory cashTransactionCategory = cashTransactionCategoryService.getCashTransactionCategoryById(transactionCategoryId);

        //TODO: Add actual user on transacted by
        final CashTransaction newCashTransaction = new CashTransaction(CashTransactionType.CASH_IN, narration, amount);
        newCashTransaction.setPaymentMethod(paymentMethod);
        newCashTransaction.setTransactionCategory(cashTransactionCategory);

        final TuitionPayment newTuitionPayment = new TuitionPayment(paymentDate, amount, narration);
        final Receipt receipt = new Receipt(newCashTransaction.getTransactionNumber());
        newTuitionPayment.setStudent(student);
        newTuitionPayment.setLessons(lessons);
        newTuitionPayment.setPaymentMethod(paymentMethod);
        newTuitionPayment.setStudent(student);
        newTuitionPayment.setReceipt(receipt);

        return tuitionPaymentRepository.save(newTuitionPayment);
    }
}