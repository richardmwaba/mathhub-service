package com.hubformath.mathhubservice.controllers.config;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.hubformath.mathhubservice.dtos.config.PaymentMethodDto;
import com.hubformath.mathhubservice.models.config.PaymentMethod;
import com.hubformath.mathhubservice.services.config.IPaymentMethodService;

@RestController
@RequestMapping(path="/api/v1/ops")
public class PaymentMethodController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPaymentMethodService paymentMethodService;

    @Autowired
    private PaymentMethodModelAssembler paymentMethodModelAssembler;

    public PaymentMethodController() {
        super();
    }

    @GetMapping("/paymentMethods")
    public ResponseEntity<CollectionModel<EntityModel<PaymentMethodDto>>> getAllPaymentMethods() {
        List<PaymentMethodDto> paymentMethods = paymentMethodService.getAllPaymentMethods().stream()
                .map(paymentMethod -> modelMapper.map(paymentMethod, PaymentMethodDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<PaymentMethodDto>> paymentMethodCollectionModel = paymentMethodModelAssembler
                .toCollectionModel(paymentMethods);

        return ResponseEntity.ok().body(paymentMethodCollectionModel);
    }

    @PostMapping("/paymentMethods")
    public ResponseEntity<EntityModel<PaymentMethodDto>> newPaymentMethod(
            @RequestBody PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethodRequest = modelMapper.map(paymentMethodDto, PaymentMethod.class);
        PaymentMethod newPaymentMethod = paymentMethodService.createPaymentMethod(paymentMethodRequest);

        EntityModel<PaymentMethodDto> paymentMethodEntityModel = paymentMethodModelAssembler
                .toModel(modelMapper.map(newPaymentMethod, PaymentMethodDto.class));

        return ResponseEntity.created(paymentMethodEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(paymentMethodEntityModel);
    }

    @GetMapping("/paymentMethods/{id}")
    public ResponseEntity<EntityModel<PaymentMethodDto>> getPaymentMethodById(@PathVariable Long id) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);

        EntityModel<PaymentMethodDto> paymentMethodEntityModel = paymentMethodModelAssembler
                .toModel(modelMapper.map(paymentMethod, PaymentMethodDto.class));

        return ResponseEntity.ok().body(paymentMethodEntityModel);
    }

    @PutMapping("/paymentMethods/{id}")
    public ResponseEntity<EntityModel<PaymentMethodDto>> replacePaymentMethod(
            @RequestBody PaymentMethodDto paymentMethodDto,
            @PathVariable Long id) {
        PaymentMethod paymentMethodRequest = modelMapper.map(paymentMethodDto, PaymentMethod.class);
        PaymentMethod updatedPaymentMethod = paymentMethodService.updatePaymentMethod(id, paymentMethodRequest);

        EntityModel<PaymentMethodDto> paymentMethodEntityModel = paymentMethodModelAssembler
                .toModel(modelMapper.map(updatedPaymentMethod, PaymentMethodDto.class));

        return ResponseEntity.ok().body(paymentMethodEntityModel);

    }

    @DeleteMapping("/paymentMethods/{id}")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);

        return ResponseEntity.ok().body("Payment method deleted successfully");
    }
}
