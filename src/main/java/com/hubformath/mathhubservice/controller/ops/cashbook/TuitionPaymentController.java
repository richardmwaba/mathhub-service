package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.ops.cashbook.TuitionPaymentDto;
import com.hubformath.mathhubservice.model.ops.cashbook.TuitionPayment;
import com.hubformath.mathhubservice.service.ops.cashbook.TuitionPaymentService;
import org.modelmapper.ModelMapper;
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
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class TuitionPaymentController {

    private final TuitionPaymentService tuitionPaymentService;

    private final ModelMapper modelMapper;

    @Autowired
    public TuitionPaymentController(final TuitionPaymentService tuitionPaymentService,
                                    final ModelMapperConfig modelMapperConfig) {
        this.tuitionPaymentService = tuitionPaymentService;
        this.modelMapper = modelMapperConfig.createModelMapper();
    }

    @GetMapping("/tuitionPayments")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<CollectionModel<EntityModel<TuitionPaymentDto>>> getAllTuitionPayments() {
        List<TuitionPaymentDto> tuitionPayments = tuitionPaymentService.getAllTuitionPayments().stream()
                                                                       .map(tuitionPayment -> modelMapper.map(
                                                                               tuitionPayment,
                                                                               TuitionPaymentDto.class))
                                                                       .toList();

        CollectionModel<EntityModel<TuitionPaymentDto>> tuitionPaymentCollectionModel = toCollectionModel(
                tuitionPayments);

        return ResponseEntity.ok().body(tuitionPaymentCollectionModel);
    }

    @PostMapping("/tuitionPayments")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT')")
    public ResponseEntity<EntityModel<TuitionPaymentDto>> newTuitionPayment(@RequestBody final TuitionPaymentDto tuitionPayment) {
        final TuitionPayment newTuitionPayment = tuitionPaymentService.createTuitionPayment(tuitionPayment);
        if (newTuitionPayment == null) {
            return ResponseEntity.noContent().build();
        }
        EntityModel<TuitionPaymentDto> tuitionPaymentEntityModel = toModel(modelMapper.map(newTuitionPayment,
                                                                                           TuitionPaymentDto.class));

        return ResponseEntity.created(tuitionPaymentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(tuitionPaymentEntityModel);
    }

    @GetMapping("/tuitionPayments/{tuitionPaymentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER') or hasRole('PARENT') or hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<EntityModel<TuitionPaymentDto>> getTuitionPaymentById(@PathVariable final UUID tuitionPaymentId) {
        try {
            TuitionPayment tuitionPayment = tuitionPaymentService.getTuitionPaymentById(tuitionPaymentId);
            EntityModel<TuitionPaymentDto> tuitionPaymentEntityModel = toModel(modelMapper.map(tuitionPayment,
                                                                                               TuitionPaymentDto.class));
            return ResponseEntity.ok().body(tuitionPaymentEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<TuitionPaymentDto> toModel(final TuitionPaymentDto tuitionPayment) {
        return EntityModel.of(tuitionPayment,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TuitionPaymentController.class)
                                                                        .getTuitionPaymentById(tuitionPayment.getTuitionPaymentId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TuitionPaymentController.class)
                                                                        .getAllTuitionPayments())
                                               .withRel("tuitionPayments"));
    }

    private CollectionModel<EntityModel<TuitionPaymentDto>> toCollectionModel(final Iterable<? extends TuitionPaymentDto> tuitionPayments) {
        List<EntityModel<TuitionPaymentDto>> tuitionPaymentList = StreamSupport.stream(tuitionPayments.spliterator(),
                                                                                       false)
                                                                               .map(this::toModel)
                                                                               .toList();

        return CollectionModel.of(tuitionPaymentList,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TuitionPaymentController.class)
                                                                            .getAllTuitionPayments())
                                                   .withSelfRel());
    }
}
