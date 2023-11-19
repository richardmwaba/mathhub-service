package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/systemconfig/ops")
@PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER')")
public class PaymentMethodController {

    private final ModelMapper modelMapper;

    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(final ModelMapperConfig modelMapperConfig,
                                   final PaymentMethodService paymentMethodService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("/paymentMethods")
    public ResponseEntity<CollectionModel<EntityModel<PaymentMethodDto>>> getAllPaymentMethods() {
        List<PaymentMethodDto> paymentMethods = paymentMethodService.getAllPaymentMethods().stream()
                                                                    .map(paymentMethod -> modelMapper.map(paymentMethod,
                                                                                                          PaymentMethodDto.class))
                                                                    .toList();

        CollectionModel<EntityModel<PaymentMethodDto>> paymentMethodCollectionModel = toCollectionModel(paymentMethods);

        return ResponseEntity.ok().body(paymentMethodCollectionModel);
    }

    @PostMapping("/paymentMethods")
    public ResponseEntity<EntityModel<PaymentMethodDto>> newPaymentMethod(@RequestBody PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethodRequest = modelMapper.map(paymentMethodDto, PaymentMethod.class);
        PaymentMethod newPaymentMethod = paymentMethodService.createPaymentMethod(paymentMethodRequest);

        EntityModel<PaymentMethodDto> paymentMethodEntityModel = toModel(modelMapper.map(newPaymentMethod,
                                                                                         PaymentMethodDto.class));

        return ResponseEntity.created(paymentMethodEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(paymentMethodEntityModel);
    }

    @GetMapping("/paymentMethods/{paymentMethodId}")
    public ResponseEntity<EntityModel<PaymentMethodDto>> getPaymentMethodById(@PathVariable final UUID paymentMethodId) {
        try {
            PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
            EntityModel<PaymentMethodDto> paymentMethodEntityModel = toModel(modelMapper.map(paymentMethod,
                                                                                             PaymentMethodDto.class));
            return ResponseEntity.ok().body(paymentMethodEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/paymentMethods/{paymentMethodId}")
    public ResponseEntity<EntityModel<PaymentMethodDto>> replacePaymentMethod(@RequestBody final PaymentMethodDto paymentMethodDto,
                                                                              @PathVariable final UUID paymentMethodId) {
        try {
            PaymentMethod paymentMethodRequest = modelMapper.map(paymentMethodDto, PaymentMethod.class);
            PaymentMethod updatedPaymentMethod = paymentMethodService.updatePaymentMethod(paymentMethodId,
                                                                                          paymentMethodRequest);
            EntityModel<PaymentMethodDto> paymentMethodEntityModel = toModel(modelMapper.map(updatedPaymentMethod,
                                                                                             PaymentMethodDto.class));
            return ResponseEntity.ok().body(paymentMethodEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/paymentMethods/{paymentMethodId}")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable final UUID paymentMethodId) {
        try {
            paymentMethodService.deletePaymentMethod(paymentMethodId);
            return ResponseEntity.ok().body("Payment method deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<PaymentMethodDto> toModel(final PaymentMethodDto paymentMethod) {
        return EntityModel.of(paymentMethod,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class)
                                                                        .getPaymentMethodById(paymentMethod.getPaymentMethodId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class)
                                                                        .getAllPaymentMethods())
                                               .withRel("paymentMethods"));
    }

    private CollectionModel<EntityModel<PaymentMethodDto>> toCollectionModel(final Iterable<? extends PaymentMethodDto> paymentMethods) {
        List<EntityModel<PaymentMethodDto>> paymentMethodList = StreamSupport
                .stream(paymentMethods.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(paymentMethodList,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class)
                                                                            .getAllPaymentMethods())
                                                   .withSelfRel());
    }
}
