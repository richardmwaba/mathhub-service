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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubformath.mathhubservice.dto.ops.cashbook.LiabilityDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Liability;
import com.hubformath.mathhubservice.service.ops.cashbook.LiabilityService;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class LiabilityController {

    private final ModelMapper modelMapper;

    private final LiabilityService liabilityService;

    @Autowired
    public LiabilityController(final ModelMapper modelMapper, final LiabilityService liabilityService) {
        this.modelMapper = modelMapper;
        this.liabilityService = liabilityService;
    }

    @GetMapping("/liabilities")
    public ResponseEntity<CollectionModel<EntityModel<LiabilityDto>>> getAllLiabilities() {
        List<LiabilityDto> liabilities = liabilityService.getAllLiabilities().stream()
                .map(liability -> modelMapper.map(liability, LiabilityDto.class))
                .toList();

        CollectionModel<EntityModel<LiabilityDto>> liabilityCollectionModel = toCollectionModel(liabilities);

        return ResponseEntity.ok().body(liabilityCollectionModel);
    }

    @PostMapping("/liabilities")
    public ResponseEntity<EntityModel<LiabilityDto>> newLiability(@RequestBody final LiabilityDto liabilityDto) {
        Liability liabilityRequest = modelMapper.map(liabilityDto, Liability.class);
        Liability newLiability = liabilityService.createLiability(liabilityRequest);

        EntityModel<LiabilityDto> liabilityEntityModel = toModel(modelMapper.map(newLiability, LiabilityDto.class));

        return ResponseEntity.created(liabilityEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(liabilityEntityModel);
    }

    @GetMapping("/liabilities/{id}")
    public ResponseEntity<EntityModel<LiabilityDto>> getLiabilityById(@PathVariable final Long id) {
        Liability liability = liabilityService.getLiabilityById(id);

        EntityModel<LiabilityDto> liabilityEntityModel = toModel(modelMapper.map(liability, LiabilityDto.class));

        return ResponseEntity.ok().body(liabilityEntityModel);
    }

    @PutMapping("/liabilities/{id}")
    public ResponseEntity<EntityModel<LiabilityDto>> replaceLiability(
            @RequestBody LiabilityDto liabilityDto,
            @PathVariable Long id) {
        Liability liabilityRequest = modelMapper.map(liabilityDto, Liability.class);
        Liability updatedLiability = liabilityService.updateLiability(id, liabilityRequest);

        EntityModel<LiabilityDto> liabilityEntityModel = toModel(modelMapper.map(updatedLiability, LiabilityDto.class));

        return ResponseEntity.ok().body(liabilityEntityModel);

    }

    @DeleteMapping("/liabilities/{id}")
    public ResponseEntity<String> deleteLiability(@PathVariable final Long id) {
        liabilityService.deleteLiability(id);

        return ResponseEntity.ok().body("Liability deleted successfully");
    }

    private EntityModel<LiabilityDto> toModel(final LiabilityDto liability) {
        return EntityModel.of(liability,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityController.class).getLiabilityById(liability.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityController.class).getAllLiabilities()).withRel("liabilities"));
    }

    private CollectionModel<EntityModel<LiabilityDto>> toCollectionModel(final Iterable<? extends LiabilityDto> liabilities) {
        List<EntityModel<LiabilityDto>> liabilityList = StreamSupport.stream(liabilities.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(liabilityList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityController.class)
                        .getAllLiabilities())
                .withSelfRel());
    }
}

