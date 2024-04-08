package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;
import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPaymentRequest;
import com.hubformath.mathhubservice.service.ops.cashbook.TuitionPaymentService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class TuitionPaymentController {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TuitionPaymentController.class);

    private final TuitionPaymentService tuitionPaymentService;

    @Autowired
    public TuitionPaymentController(TuitionPaymentService tuitionPaymentService) {
        this.tuitionPaymentService = tuitionPaymentService;
    }

    @GetMapping("/tuitionPayments")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<CollectionModel<EntityModel<TuitionPayment>>> getAllTuitionPayments() {
        CollectionModel<EntityModel<TuitionPayment>> tuitionPaymentCollectionModel = toCollectionModel(
                tuitionPaymentService.getAllTuitionPayments());
        return ResponseEntity.ok().body(tuitionPaymentCollectionModel);
    }

    @PostMapping("/tuitionPayments")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    @SuppressWarnings("java:S1452") // Wildcard type required for multiple return types
    public ResponseEntity<?> newTuitionPayment(@RequestBody TuitionPaymentRequest tuitionPaymentRequest) {
        try {
            final TuitionPayment newTuitionPayment = tuitionPaymentService.createTuitionPayment(tuitionPaymentRequest);
            if (newTuitionPayment == null) {
                return ResponseEntity.ok().body("Student is not currently not owing any payment or has already paid.");
            }

            EntityModel<TuitionPayment> tuitionPaymentEntityModel = toModel(newTuitionPayment);

            return ResponseEntity.created(tuitionPaymentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                 .body(tuitionPaymentEntityModel);
        } catch (Exception e) {
            LOGGER.error("An error occurred while processing the request.", e);
            return ResponseEntity.internalServerError().body("An error occurred while processing the request. Please try again.");
        }
    }

    @GetMapping("/tuitionPayments/{tuitionPaymentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<EntityModel<TuitionPayment>> getTuitionPaymentById(@PathVariable String tuitionPaymentId) {
        try {
            EntityModel<TuitionPayment> tuitionPayment = toModel(tuitionPaymentService.getTuitionPaymentById(tuitionPaymentId));
            return ResponseEntity.ok().body(tuitionPayment);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<TuitionPayment> toModel(TuitionPayment tuitionPayment) {
        return EntityModel.of(tuitionPayment,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TuitionPaymentController.class)
                                                                        .getTuitionPaymentById(tuitionPayment.getTuitionPaymentId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TuitionPaymentController.class)
                                                                        .getAllTuitionPayments())
                                               .withRel("tuitionPayments"));
    }

    private CollectionModel<EntityModel<TuitionPayment>> toCollectionModel(Iterable<? extends TuitionPayment> tuitionPaymentsIterable) {
        List<EntityModel<TuitionPayment>> tuitionPayments = StreamSupport.stream(tuitionPaymentsIterable.spliterator(),
                                                                                 false)
                                                                         .map(this::toModel)
                                                                         .toList();

        return CollectionModel.of(tuitionPayments,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TuitionPaymentController.class)
                                                                            .getAllTuitionPayments())
                                                   .withSelfRel());
    }
}
