package com.hubformath.mathhubservice.controller.systemconfig;

import java.util.List;
import java.util.stream.StreamSupport;

import com.hubformath.mathhubservice.service.systemconfig.LiabilityTypeService;
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

import com.hubformath.mathhubservice.dto.systemconfig.LiabilityTypeDto;
import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;

@RestController
@RequestMapping(path="/api/v1/systemconfig/ops")
public class LiabilityTypeController {

    private final ModelMapper modelMapper;

    private final LiabilityTypeService liabilityTypeService;

    @Autowired
    public LiabilityTypeController(final ModelMapper modelMapper, final LiabilityTypeService liabilityTypeService) {
        this.modelMapper = modelMapper;
        this.liabilityTypeService = liabilityTypeService;
    }

    @GetMapping("/liabilityTypes")
    public ResponseEntity<CollectionModel<EntityModel<LiabilityTypeDto>>> getAllLiabilityTypes() {
        List<LiabilityTypeDto> liabilityTypes = liabilityTypeService.getAllLiabilityTypes().stream()
                .map(liabilityType -> modelMapper.map(liabilityType, LiabilityTypeDto.class))
                .toList();

        CollectionModel<EntityModel<LiabilityTypeDto>> liabilityTypeCollectionModel = toCollectionModel(liabilityTypes);

        return ResponseEntity.ok().body(liabilityTypeCollectionModel);
    }

    @PostMapping("/liabilityTypes")
    public ResponseEntity<EntityModel<LiabilityTypeDto>> newLiabilityType(@RequestBody final LiabilityTypeDto liabilityTypeDto) {
        LiabilityType liabilityTypeRequest = modelMapper.map(liabilityTypeDto, LiabilityType.class);
        LiabilityType newLiabilityType = liabilityTypeService.createLiabilityType(liabilityTypeRequest);
        EntityModel<LiabilityTypeDto> liabilityTypeEntityModel = toModel(modelMapper.map(newLiabilityType, LiabilityTypeDto.class));

        return ResponseEntity.created(liabilityTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(liabilityTypeEntityModel);
    }

    @GetMapping("/liabilityTypes/{id}")
    public ResponseEntity<EntityModel<LiabilityTypeDto>> getLiabilityTypeById(@PathVariable final Long id) {
        LiabilityType liabilityType = liabilityTypeService.getLiabilityTypeById(id);
        EntityModel<LiabilityTypeDto> liabilityTypeEntityModel = toModel(modelMapper.map(liabilityType, LiabilityTypeDto.class));

        return ResponseEntity.ok().body(liabilityTypeEntityModel);
    }

    @PutMapping("/liabilityTypes/{id}")
    public ResponseEntity<EntityModel<LiabilityTypeDto>> replaceLiabilityType(
            @RequestBody final LiabilityTypeDto liabilityTypeDto,
            @PathVariable final Long id) {
        LiabilityType liabilityTypeRequest = modelMapper.map(liabilityTypeDto, LiabilityType.class);
        LiabilityType updatedLiabilityType = liabilityTypeService.updateLiabilityType(id, liabilityTypeRequest);
        EntityModel<LiabilityTypeDto> liabilityTypeEntityModel = toModel(modelMapper.map(updatedLiabilityType, LiabilityTypeDto.class));

        return ResponseEntity.ok().body(liabilityTypeEntityModel);

    }

    @DeleteMapping("/liabilityTypes/{id}")
    public ResponseEntity<String> deleteLiabilityType(@PathVariable final Long id) {
        liabilityTypeService.deleteLiabilityType(id);
        return ResponseEntity.ok().body("Liability type deleted successfully");
    }

    private EntityModel<LiabilityTypeDto> toModel(final LiabilityTypeDto assessmentType) {
        return EntityModel.of(assessmentType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityTypeController.class).getLiabilityTypeById(assessmentType.getId()))
                        .withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityTypeController.class).getAllLiabilityTypes()).withRel("liabilityTypes"));
    }

    private CollectionModel<EntityModel<LiabilityTypeDto>> toCollectionModel(final Iterable<? extends LiabilityTypeDto> assessmentTypes) {
        List<EntityModel<LiabilityTypeDto>> assessmentTypeList = StreamSupport
                .stream(assessmentTypes.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(assessmentTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityTypeController.class)
                        .getAllLiabilityTypes())
                .withSelfRel());
    }
}
