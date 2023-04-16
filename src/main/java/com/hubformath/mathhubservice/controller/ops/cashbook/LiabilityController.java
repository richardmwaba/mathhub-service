package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.ops.cashbook.LiabilityDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Liability;
import com.hubformath.mathhubservice.service.ops.cashbook.LiabilityService;
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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/ops")
public class LiabilityController {

    private final ModelMapper modelMapper;

    private final LiabilityService liabilityService;

    @Autowired
    public LiabilityController(final ModelMapperConfig modelMapperConfig, final LiabilityService liabilityService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
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
    public ResponseEntity<EntityModel<LiabilityDto>> newLiability(@RequestBody final LiabilityDto liability) {
        Liability liabilityRequest = modelMapper.map(liability, Liability.class);
        Liability newLiability = liabilityService.createLiability(liabilityRequest);
        EntityModel<LiabilityDto> liabilityEntityModel = toModel(modelMapper.map(newLiability, LiabilityDto.class));

        return ResponseEntity.created(liabilityEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(liabilityEntityModel);
    }

    @GetMapping("/liabilities/{liabilityId}")
    public ResponseEntity<EntityModel<LiabilityDto>> getLiabilityById(@PathVariable final UUID liabilityId) {
        try {
            Liability liability = liabilityService.getLiabilityById(liabilityId);
            EntityModel<LiabilityDto> liabilityEntityModel = toModel(modelMapper.map(liability, LiabilityDto.class));
            return ResponseEntity.ok().body(liabilityEntityModel);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/liabilities/{liabilityId}")
    public ResponseEntity<EntityModel<LiabilityDto>> replaceLiability(@RequestBody LiabilityDto liabilityDto,
                                                                      @PathVariable UUID liabilityId) {
        try {
            Liability liabilityRequest = modelMapper.map(liabilityDto, Liability.class);
            Liability updatedLiability = liabilityService.updateLiability(liabilityId, liabilityRequest);
            EntityModel<LiabilityDto> liabilityEntityModel = toModel(modelMapper.map(updatedLiability, LiabilityDto.class));
            return ResponseEntity.ok().body(liabilityEntityModel);
        } catch (NoSuchElementException e) {
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/liabilities/{liabilityId}")
    public ResponseEntity<String> deleteLiability(@PathVariable final UUID liabilityId) {
        try {
            liabilityService.deleteLiability(liabilityId);
            return ResponseEntity.ok().body("Liability deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<LiabilityDto> toModel(final LiabilityDto liability) {
        return EntityModel.of(liability,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityController.class).getLiabilityById(liability.getLiabilityId())).withSelfRel(),
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

