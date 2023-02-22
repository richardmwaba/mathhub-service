package com.hubformath.mathhubservice.controller.ops.cashbook;


import java.util.List;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.hubformath.mathhubservice.dto.ops.cashbook.EquityDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Equity;
import com.hubformath.mathhubservice.service.ops.cashbook.EquityService;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class EquityController {

    private final ModelMapper modelMapper;

    private final EquityService equityService;

    @Autowired
    public EquityController(final ModelMapper modelMapper, final EquityService equityService) {
        this.modelMapper = modelMapper;
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
    public ResponseEntity<EntityModel<EquityDto>> createEquity( @RequestBody final EquityDto equityDto) {
        Equity equityRequest = modelMapper.map(equityDto, Equity.class);
        Equity newEquity = equityService.createEquity(equityRequest);

        EntityModel<EquityDto> equityEntityModel = toModel(modelMapper.map(newEquity, EquityDto.class));

        return ResponseEntity.created(equityEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(equityEntityModel);
    }

    @GetMapping("/equity/{id}")
    public ResponseEntity<EntityModel<EquityDto>> getEquityById(@PathVariable final Long id) {
        Equity equity = equityService.getEquityById(id);

        EntityModel<EquityDto> equityEntityModel = toModel(modelMapper.map(equity, EquityDto.class));

        return ResponseEntity.ok().body(equityEntityModel);
    }

    @PatchMapping("/equity/{id}")
    public ResponseEntity<EntityModel<EquityDto>> updateEquity(
            @RequestBody final EquityDto equityDto,
            @PathVariable final Long id) {
        Equity equityRequest = modelMapper.map(equityDto, Equity.class);
        Equity updatedEquity = equityService.updateEquity(id, equityRequest);

        EntityModel<EquityDto> equityEntityModel = toModel(modelMapper.map(updatedEquity, EquityDto.class));

        return ResponseEntity.ok().body(equityEntityModel);

    }

    @DeleteMapping("/equity/{id}")
    public ResponseEntity<String> deleteEquity(@PathVariable Long id) {
        equityService.deleteEquity(id);

        return ResponseEntity.ok().body("Equity deleted successfully");
    }

    private EntityModel<EquityDto> toModel(final EquityDto equity) {
        return EntityModel.of(equity,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class).getEquityById(equity.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class).getAllEquity()).withRel("equity"));
    }

    private CollectionModel<EntityModel<EquityDto>> toCollectionModel(final Iterable<? extends EquityDto> equity) {
        List<EntityModel<EquityDto>> equityList = StreamSupport.stream(equity.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(equityList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityController.class)
                        .getAllEquity())
                .withSelfRel());
    }
    
}
