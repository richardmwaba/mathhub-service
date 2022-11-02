package com.hubformath.mathhubservice.controllers.config;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.assemblers.config.PaymentMethodModelAssembler;
import com.hubformath.mathhubservice.models.config.PaymentMethod;
import com.hubformath.mathhubservice.repositories.config.PaymentMethodRepository;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@RestController
@RequestMapping(path="/v1/api/ops")
public class PaymentMethodController {
    private final PaymentMethodRepository repository;
    private final PaymentMethodModelAssembler assembler;

    public PaymentMethodController(PaymentMethodRepository repository, PaymentMethodModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/paymentMethods")
    public CollectionModel<EntityModel<PaymentMethod>> all() {
        List<EntityModel<PaymentMethod>> paymentMethods = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(paymentMethods, linkTo(methodOn(PaymentMethodController.class).all()).withSelfRel());
    }

    @PostMapping("/paymentMethods")
    public ResponseEntity<EntityModel<PaymentMethod>> newPaymentMethod(@RequestBody PaymentMethod newPaymentMethod) {
        EntityModel<PaymentMethod> entityModel = assembler.toModel(repository.save(newPaymentMethod));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/paymentMethods/{id}")
    public EntityModel<PaymentMethod> one(@PathVariable Long id) {
        PaymentMethod paymentMethod = repository.findById(id) //
                .orElseThrow(() -> new ItemNotFoundException(id, "paymentMethod"));

        return assembler.toModel(paymentMethod);
    }

    @PutMapping("/paymentMethods/{id}")
    public ResponseEntity<EntityModel<PaymentMethod>> replacePaymentMethod(@RequestBody PaymentMethod newPaymentMethod,
            @PathVariable Long id) {
        PaymentMethod updatedPaymentMethod = repository.findById(id) //
                .map(paymentMethod -> {
                    paymentMethod.setTypeName(newPaymentMethod.getTypeName());
                    paymentMethod.setTypeDescription(newPaymentMethod.getTypeDescription());
                    return repository.save(paymentMethod);
                }) //
                .orElseThrow(() -> new ItemNotFoundException(id, "payment method"));

        EntityModel<PaymentMethod> entityModel = assembler.toModel(updatedPaymentMethod);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/paymentMethods/{id}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
