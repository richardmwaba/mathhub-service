package com.hubformath.mathhubservice.controller.ops.cashbook;


import com.hubformath.mathhubservice.model.ops.cashbook.Equity;
import com.hubformath.mathhubservice.model.ops.cashbook.EquityRequest;
import com.hubformath.mathhubservice.service.ops.cashbook.EquityService;
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
public class EquityController {

    private final EquityService equityService;

    @Autowired
    public EquityController(EquityService equityService) {
        this.equityService = equityService;
    }

    @GetMapping("/equity")
    public ResponseEntity<CollectionModel<EntityModel<Equity>>> getAllEquity() {
        CollectionModel<EntityModel<Equity>> equities = toCollectionModel(equityService.getAllEquity());
        return ResponseEntity.ok().body(equities);
    }

    @PostMapping("/equity")
    public ResponseEntity<EntityModel<Equity>> createEquity(@RequestBody EquityRequest equityRequest) {
        EntityModel<Equity> newEquity = toModel(equityService.createEquity(equityRequest));

        return ResponseEntity.created(newEquity.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(newEquity);
    }

    @GetMapping("/equity/{equityId}")
    public ResponseEntity<EntityModel<Equity>> getEquityById(@PathVariable String equityId) {
        try {
            EntityModel<Equity> equity = toModel(equityService.getEquityById(equityId));
            return ResponseEntity.ok().body(equity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/equity/{equityId}")
    public ResponseEntity<EntityModel<Equity>> updateEquity(@RequestBody final EquityRequest equityRequest,
                                                            @PathVariable final String equityId) {
        try {
            EntityModel<Equity> updatedEquity = toModel(equityService.updateEquity(equityId, equityRequest));
            return ResponseEntity.ok().body(updatedEquity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/equity/{equityId}")
    public ResponseEntity<String> deleteEquity(@PathVariable String equityId) {
        try {
            equityService.deleteEquity(equityId);
            return ResponseEntity.ok().body("Equity deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<Equity> toModel(final Equity equity) {
        return EntityModel.of(equity,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class)
                                                                        .getEquityById(equity.getEquityId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class)
                                                                        .getAllEquity()).withRel("equity"));
    }

    private CollectionModel<EntityModel<Equity>> toCollectionModel(final Iterable<? extends Equity> equityIterable) {
        List<EntityModel<Equity>> equities = StreamSupport.stream(equityIterable.spliterator(), false)
                                                          .map(this::toModel)
                                                          .toList();

        return CollectionModel.of(equities,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class)
                                                                            .getAllEquity())
                                                   .withSelfRel());
    }

}
