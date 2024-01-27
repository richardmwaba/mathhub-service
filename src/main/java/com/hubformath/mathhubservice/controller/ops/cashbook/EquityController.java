package com.hubformath.mathhubservice.controller.ops.cashbook;


import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.ops.cashbook.EquityDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Equity;
import com.hubformath.mathhubservice.service.ops.cashbook.EquityService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class EquityController {

    private final ModelMapper modelMapper;

    private final EquityService equityService;

    @Autowired
    public EquityController(final ModelMapperConfig modelMapperConfig, final EquityService equityService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.equityService = equityService;
    }

    @GetMapping("/equity")
    public ResponseEntity<CollectionModel<EntityModel<EquityDto>>> getAllEquity() {
        List<EquityDto> equityList = equityService.getAllEquity().stream()
                                                  .map(equity -> modelMapper.map(equity, EquityDto.class))
                                                  .toList();

        CollectionModel<EntityModel<EquityDto>> equityCollectionModel = toCollectionModel(equityList);

        return ResponseEntity.ok().body(equityCollectionModel);
    }

    @PostMapping("/equity")
    public ResponseEntity<EntityModel<EquityDto>> createEquity(@RequestBody final EquityDto equityDto) {
        Equity equityRequest = modelMapper.map(equityDto, Equity.class);
        Equity newEquity = equityService.createEquity(equityRequest);

        EntityModel<EquityDto> equityEntityModel = toModel(modelMapper.map(newEquity, EquityDto.class));

        return ResponseEntity.created(equityEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(equityEntityModel);
    }

    @GetMapping("/equity/{equityId}")
    public ResponseEntity<EntityModel<EquityDto>> getEquityById(@PathVariable final UUID equityId) {
        try {
            Equity equity = equityService.getEquityById(equityId);
            EntityModel<EquityDto> equityEntityModel = toModel(modelMapper.map(equity, EquityDto.class));
            return ResponseEntity.ok().body(equityEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/equity/{equityId}")
    public ResponseEntity<EntityModel<EquityDto>> updateEquity(@RequestBody final EquityDto equityDto,
                                                               @PathVariable final UUID equityId) {
        try {
            Equity equityRequest = modelMapper.map(equityDto, Equity.class);
            Equity updatedEquity = equityService.updateEquity(equityId, equityRequest);
            EntityModel<EquityDto> equityEntityModel = toModel(modelMapper.map(updatedEquity, EquityDto.class));
            return ResponseEntity.ok().body(equityEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/equity/{equityId}")
    public ResponseEntity<String> deleteEquity(@PathVariable UUID equityId) {
        try {
            equityService.deleteEquity(equityId);
            return ResponseEntity.ok().body("Equity deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<EquityDto> toModel(final EquityDto equity) {
        return EntityModel.of(equity,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class)
                                                                        .getEquityById(equity.getEquityId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class)
                                                                        .getAllEquity()).withRel("equity"));
    }

    private CollectionModel<EntityModel<EquityDto>> toCollectionModel(final Iterable<? extends EquityDto> equity) {
        List<EntityModel<EquityDto>> equityList = StreamSupport.stream(equity.spliterator(), false)
                                                               .map(this::toModel)
                                                               .toList();

        return CollectionModel.of(equityList,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class)
                                                                            .getAllEquity())
                                                   .withSelfRel());
    }

}
