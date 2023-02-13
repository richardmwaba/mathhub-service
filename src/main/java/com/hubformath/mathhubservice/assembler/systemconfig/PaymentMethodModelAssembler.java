package com.hubformath.mathhubservice.assembler.systemconfig;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controller.systemconfig.PaymentMethodController;
import com.hubformath.mathhubservice.dto.systemconfig.PaymentMethodDto;

@Component
public class PaymentMethodModelAssembler implements RepresentationModelAssembler<PaymentMethodDto, EntityModel<PaymentMethodDto>> {
    @Override
    public EntityModel<PaymentMethodDto> toModel(PaymentMethodDto paymentMethod) {
        return EntityModel.of(paymentMethod,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class).getPaymentMethodById(paymentMethod.getId()))
                        .withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class).getAllPaymentMethods()).withRel("paymentMethods"));
    }

    @Override
    public CollectionModel<EntityModel<PaymentMethodDto>> toCollectionModel(
            Iterable<? extends PaymentMethodDto> paymentMethods) {
        List<EntityModel<PaymentMethodDto>> paymentMethodList = StreamSupport
                .stream(paymentMethods.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(paymentMethodList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class)
                .getAllPaymentMethods())
                .withSelfRel());
    }
}
