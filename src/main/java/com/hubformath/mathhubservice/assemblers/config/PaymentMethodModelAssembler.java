package com.hubformath.mathhubservice.assemblers.config;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.controllers.config.PaymentMethodController;
import com.hubformath.mathhubservice.models.config.PaymentMethod;

@Component
public class PaymentMethodModelAssembler implements RepresentationModelAssembler<PaymentMethod, EntityModel<PaymentMethod>> {
    @Override
    public EntityModel<PaymentMethod> toModel(PaymentMethod paymentMethod) {

    return EntityModel.of(paymentMethod, //
        linkTo(methodOn(PaymentMethodController.class).one(paymentMethod.getId())).withSelfRel(),
        linkTo(methodOn(PaymentMethodController.class).all()).withRel("paymentMethods"));
  }
}
