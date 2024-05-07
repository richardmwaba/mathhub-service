package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import com.hubformath.mathhubservice.service.systemconfig.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
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

@RestController
@RequestMapping(path = "/api/v1/systemconfig/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(final PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("/paymentMethods")
    public ResponseEntity<CollectionModel<?>> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
        return ResponseEntity.ok().body(toCollectionModel(paymentMethods));
    }

    @PostMapping("/paymentMethods")
    public ResponseEntity<EntityModel<PaymentMethod>> newPaymentMethod(@RequestBody PaymentMethod paymentMethodRequest) {
        EntityModel<PaymentMethod> newPaymentMethod = toModel(paymentMethodService.createPaymentMethod(
                paymentMethodRequest));
        return ResponseEntity.created(newPaymentMethod.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(newPaymentMethod);
    }

    @GetMapping("/paymentMethods/{paymentMethodId}")
    public ResponseEntity<EntityModel<PaymentMethod>> getPaymentMethodById(@PathVariable String paymentMethodId) {
        try {
            EntityModel<PaymentMethod> newPaymentMethod = toModel(paymentMethodService.getPaymentMethodById(
                    paymentMethodId));
            return ResponseEntity.ok().body(newPaymentMethod);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/paymentMethods/{paymentMethodId}")
    public ResponseEntity<EntityModel<PaymentMethod>> replacePaymentMethod(@RequestBody PaymentMethod paymentMethodRequest,
                                                                           @PathVariable String paymentMethodId) {
        try {
            EntityModel<PaymentMethod> paymentMethodEntityModel = toModel(paymentMethodService.updatePaymentMethod(
                    paymentMethodId,
                    paymentMethodRequest));
            return ResponseEntity.ok().body(paymentMethodEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/paymentMethods/{paymentMethodId}")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable String paymentMethodId) {
        try {
            paymentMethodService.deletePaymentMethod(paymentMethodId);
            return ResponseEntity.ok().body("Payment method deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<PaymentMethod> toModel(PaymentMethod paymentMethod) {
        return EntityModel.of(paymentMethod,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class)
                                                                        .getPaymentMethodById(paymentMethod.getPaymentMethodId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class)
                                                                        .getAllPaymentMethods())
                                               .withRel("paymentMethods"));
    }

    private CollectionModel<?> toCollectionModel(List<PaymentMethod> paymentMethodList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentMethodController.class)
                                                              .getAllPaymentMethods()).withSelfRel();
        List<EntityModel<PaymentMethod>> paymentMethods = paymentMethodList.stream()
                                                                           .map(this::toModel)
                                                                           .toList();

        return paymentMethodList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(PaymentMethod.class, link)
                : CollectionModel.of(paymentMethods, link);
    }
}
