package com.hubformath.mathhubservice.ops.config.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hubformath.mathhubservice.ops.config.models.PaymentMethod;
import com.hubformath.mathhubservice.ops.config.controllers.PaymentMethodController;

@Component
public class PaymentMethodModelAssembler implements RepresentationModelAssembler<PaymentMethod, EntityModel<PaymentMethod>> {
    @Override
    public EntityModel<PaymentMethod> toModel(PaymentMethod paymentMethod) {

    return EntityModel.of(paymentMethod, //
        linkTo(methodOn(PaymentMethodController.class).one(paymentMethod.getId())).withSelfRel(),
        linkTo(methodOn(PaymentMethodController.class).all()).withRel("paymentMethods"));
  }
}
