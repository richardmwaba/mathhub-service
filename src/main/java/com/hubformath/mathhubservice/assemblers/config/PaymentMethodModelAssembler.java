package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.PaymentMethodController;
import com.hubformath.mathhubservice.dtos.config.PaymentMethodDto;

@Component
public class PaymentMethodModelAssembler
        implements RepresentationModelAssembler<PaymentMethodDto, EntityModel<PaymentMethodDto>> {
    @Override
    public EntityModel<PaymentMethodDto> toModel(PaymentMethodDto paymentMethod) {

        return EntityModel.of(paymentMethod,
                linkTo(methodOn(PaymentMethodController.class).getPaymentMethodById(paymentMethod.getId()))
                        .withSelfRel(),
                linkTo(methodOn(PaymentMethodController.class).getAllPaymentMethods()).withRel("paymentMethods"));
    }

    @Override
    public CollectionModel<EntityModel<PaymentMethodDto>> toCollectionModel(
            Iterable<? extends PaymentMethodDto> paymentMethods) {
        List<EntityModel<PaymentMethodDto>> paymentMethodList = StreamSupport
                .stream(paymentMethods.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(paymentMethodList, linkTo(methodOn(PaymentMethodController.class)
                .getAllPaymentMethods())
                .withSelfRel());
    }
}
