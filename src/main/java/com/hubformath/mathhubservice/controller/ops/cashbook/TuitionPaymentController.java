package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.dto.ops.cashbook.TuitionPaymentDto;
import com.hubformath.mathhubservice.dto.ops.cashbook.TuitionPaymentRequestDto;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransactionType;
import com.hubformath.mathhubservice.model.ops.cashbook.Receipt;
import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;
import com.hubformath.mathhubservice.model.sis.Lessons;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.CashTransactionCategory;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.service.ops.cashbook.TuitionPaymentService;
import com.hubformath.mathhubservice.service.sis.LessonsService;
import com.hubformath.mathhubservice.service.sis.StudentService;
import com.hubformath.mathhubservice.service.systemconfig.CashTransactionCategoryService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class TuitionPaymentController {

    private final TuitionPaymentService tuitionPaymentService;

    private final PaymentMethodService paymentMethodService;

    private final LessonsService lessonsService;

    private final CashTransactionCategoryService cashTransactionCategoryService;

    private final StudentService studentService;

    private final ModelMapper modelMapper;

    @Autowired
    public TuitionPaymentController(final TuitionPaymentService tuitionPaymentService,
                                    final PaymentMethodService paymentMethodService,
                                    final LessonsService lessonsService,
                                    final CashTransactionCategoryService cashTransactionCategoryService,
                                    final StudentService studentService,
                                    final ModelMapper modelMapper) {
        this.tuitionPaymentService = tuitionPaymentService;
        this.paymentMethodService = paymentMethodService;
        this.lessonsService = lessonsService;
        this.cashTransactionCategoryService = cashTransactionCategoryService;
        this.studentService = studentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/tuitionPayments")
    public ResponseEntity<CollectionModel<EntityModel<TuitionPaymentDto>>> getAllTuitionPayments() {
        List<TuitionPaymentDto> tuitionPayments = tuitionPaymentService.getAllTuitionPayments().stream()
                .map(tuitionPayment -> modelMapper.map(tuitionPayment, TuitionPaymentDto.class))
                .toList();

        CollectionModel<EntityModel<TuitionPaymentDto>> tuitionPaymentCollectionModel = toCollectionModel(tuitionPayments);

        return ResponseEntity.ok().body(tuitionPaymentCollectionModel);
    }

    @PostMapping("/tuitionPayments")
    public ResponseEntity<EntityModel<TuitionPaymentDto>> newTuitionPayment(
            @RequestBody final TuitionPaymentRequestDto tuitionPaymentRequestDto) {
        final Long paymentMethodId = tuitionPaymentRequestDto.getPaymentMethodId();
        final Long studentId = tuitionPaymentRequestDto.getStudentId();
        final Long lessonsId = tuitionPaymentRequestDto.getLessonsId();
        final Long transactionCategoryId = tuitionPaymentRequestDto.getTransactionCategoryId();
        final String narration = tuitionPaymentRequestDto.getNarration();
        final Double amount = tuitionPaymentRequestDto.getAmount();

        final Lessons lessons = lessonsService.getLessonsById(lessonsId);
        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
        final Student student = studentService.getStudentById(studentId);
        final CashTransactionCategory cashTransactionCategory = cashTransactionCategoryService.getCashTransactionCategoryById(transactionCategoryId);

        final CashTransaction newCashTransaction = new CashTransaction(paymentMethod, CashTransactionType.CASH_IN, cashTransactionCategory, narration, amount);
        final Receipt receipt = new Receipt(newCashTransaction.getTransactionNumber());

        final TuitionPayment newTuitionPayment = new TuitionPayment(newCashTransaction, student, lessons, paymentMethod, amount, receipt, narration);

        EntityModel<TuitionPaymentDto> tuitionPaymentEntityModel = toModel(modelMapper.map(tuitionPaymentService.createTuitionPayment(newTuitionPayment), TuitionPaymentDto.class));

        return ResponseEntity.created(tuitionPaymentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(tuitionPaymentEntityModel);
    }

    @GetMapping("/tuitionPayments/{id}")
    public ResponseEntity<EntityModel<TuitionPaymentDto>> getTuitionPaymentById(@PathVariable final Long id) {
        TuitionPayment tuitionPayment = tuitionPaymentService.getTuitionPaymentById(id);

        EntityModel<TuitionPaymentDto> tuitionPaymentEntityModel = toModel(modelMapper.map(tuitionPayment, TuitionPaymentDto.class));

        return ResponseEntity.ok().body(tuitionPaymentEntityModel);
    }

    private EntityModel<TuitionPaymentDto> toModel(final TuitionPaymentDto tuitionPayment) {
        return EntityModel.of(tuitionPayment,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TuitionPaymentController.class).getTuitionPaymentById(tuitionPayment.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TuitionPaymentController.class).getAllTuitionPayments()).withRel("tuitionPayments"));
    }

    private CollectionModel<EntityModel<TuitionPaymentDto>> toCollectionModel(final Iterable<? extends TuitionPaymentDto> tuitionPayments) {
        List<EntityModel<TuitionPaymentDto>> tuitionPaymentList = StreamSupport.stream(tuitionPayments.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(tuitionPaymentList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TuitionPaymentController.class)
                        .getAllTuitionPayments())
                .withSelfRel());
    }
}