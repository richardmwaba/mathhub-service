package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.dto.ops.cashbook.TuitionPaymentDto;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransaction;
import com.hubformath.mathhubservice.model.ops.cashbook.CashTransactionType;
import com.hubformath.mathhubservice.model.ops.cashbook.Receipt;
import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;
import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.service.ops.cashbook.TuitionPaymentService;
import com.hubformath.mathhubservice.service.sis.StudentService;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class TuitionPaymentController {

    private final TuitionPaymentService tuitionPaymentService;

    private final PaymentMethodService paymentMethodService;


    private final StudentService studentService;

    private final ModelMapper modelMapper;

    @Autowired
    public TuitionPaymentController(final TuitionPaymentService tuitionPaymentService,
                                    final PaymentMethodService paymentMethodService,
                                    final StudentService studentService,
                                    final ModelMapper modelMapper) {
        this.tuitionPaymentService = tuitionPaymentService;
        this.paymentMethodService = paymentMethodService;
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
            @RequestBody final TuitionPaymentDto tuitionPaymentDto) {
        final Long paymentMethodId = tuitionPaymentDto.getPaymentMethodId();
        final Long studentId = tuitionPaymentDto.getStudentId();
        final String narration = tuitionPaymentDto.getNarration();
        final Double amount = tuitionPaymentDto.getAmount();

        final PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
        final Student student = studentService.getStudentById(studentId);

        final CashTransaction newCashTransaction = new CashTransaction(paymentMethod, CashTransactionType.CASH_IN, narration, amount);
        final Receipt receipt = new Receipt(newCashTransaction.getTransactionNumber());

        final TuitionPayment newTuitionPayment = new TuitionPayment(newCashTransaction, student, paymentMethod, amount, receipt, narration);

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
