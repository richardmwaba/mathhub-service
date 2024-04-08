package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Liability;
import com.hubformath.mathhubservice.model.ops.cashbook.LiabilityRequest;
import com.hubformath.mathhubservice.service.ops.cashbook.LiabilityService;
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
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class LiabilityController {

    private final LiabilityService liabilityService;

    @Autowired
    public LiabilityController(LiabilityService liabilityService) {
        this.liabilityService = liabilityService;
    }

    @GetMapping("/liabilities")
    public ResponseEntity<CollectionModel<EntityModel<Liability>>> getAllLiabilities() {
        CollectionModel<EntityModel<Liability>> liabilities = toCollectionModel(liabilityService.getAllLiabilities());
        return ResponseEntity.ok().body(liabilities);
    }

    @PostMapping("/liabilities")
    public ResponseEntity<EntityModel<Liability>> createLiability(@RequestBody LiabilityRequest liabilityRequest) {
        EntityModel<Liability> liabilityEntityModel = toModel(liabilityService.createLiability(liabilityRequest));

        return ResponseEntity.created(liabilityEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(liabilityEntityModel);
    }

    @GetMapping("/liabilities/{liabilityId}")
    public ResponseEntity<EntityModel<Liability>> getLiabilityById(@PathVariable String liabilityId) {
        try {
            EntityModel<Liability> liability = toModel(liabilityService.getLiabilityById(liabilityId));
            return ResponseEntity.ok().body(liability);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/liabilities/{liabilityId}")
    public ResponseEntity<EntityModel<Liability>> replaceLiability(@RequestBody LiabilityRequest liabilityRequest,
                                                                   @PathVariable String liabilityId) {
        try {
            EntityModel<Liability> updatedLiability = toModel(liabilityService.updateLiability(liabilityId, liabilityRequest));
            return ResponseEntity.ok().body(updatedLiability);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/liabilities/{liabilityId}")
    public ResponseEntity<String> deleteLiability(@PathVariable final String liabilityId) {
        try {
            liabilityService.deleteLiability(liabilityId);
            return ResponseEntity.ok().body("Liability deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<Liability> toModel(final Liability liability) {
        return EntityModel.of(liability,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityController.class)
                                                                        .getLiabilityById(liability.getLiabilityId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityController.class)
                                                                        .getAllLiabilities()).withRel("liabilities"));
    }

    private CollectionModel<EntityModel<Liability>> toCollectionModel(final Iterable<? extends Liability> liabilitiesIterable) {
        List<EntityModel<Liability>> liabilities = StreamSupport.stream(liabilitiesIterable.spliterator(), false)
                                                                .map(this::toModel)
                                                                .toList();

        return CollectionModel.of(liabilities,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityController.class)
                                                                            .getAllLiabilities())
                                                   .withSelfRel());
    }
}

