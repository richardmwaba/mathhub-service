package com.hubformath.mathhubservice.controller.systemconfig;

import java.util.List;
import java.util.stream.StreamSupport;

import com.hubformath.mathhubservice.service.systemconfig.EquityTypeService;
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

import com.hubformath.mathhubservice.dto.systemconfig.EquityTypeDto;
import com.hubformath.mathhubservice.model.systemconfig.EquityType;

@RestController
@RequestMapping(path="/api/v1/systemconfig/ops")
public class EquityTypeController {

    private final ModelMapper modelMapper;

    private final EquityTypeService equityTypeService;

    @Autowired
    public EquityTypeController(final ModelMapper modelMapper, final EquityTypeService equityTypeService) {
        this.modelMapper = modelMapper;
        this.equityTypeService = equityTypeService;
    }

    @GetMapping("/equityTypes")
    public ResponseEntity<CollectionModel<EntityModel<EquityTypeDto>>> getAllEquityTypes() {
        List<EquityTypeDto> equityTypes = equityTypeService.getAllEquityTypes().stream()
                .map(equityType -> modelMapper.map(equityType, EquityTypeDto.class))
                .toList();

        CollectionModel<EntityModel<EquityTypeDto>> equityTypeCollectionModel = toCollectionModel(equityTypes);

        return ResponseEntity.ok().body(equityTypeCollectionModel);
    }

    @PostMapping("/equityTypes")
    public ResponseEntity<EntityModel<EquityTypeDto>> newEquityType(@RequestBody final EquityTypeDto equityTypeDto) {
        EquityType equityTypeRequest = modelMapper.map(equityTypeDto, EquityType.class);
        EquityType newEquityType = equityTypeService.createEquityType(equityTypeRequest);
        EntityModel<EquityTypeDto> equityTypeEntityModel = toModel(modelMapper.map(newEquityType, EquityTypeDto.class));

        return ResponseEntity.created(equityTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(equityTypeEntityModel);
    }

    @GetMapping("/equityTypes/{id}")
    public ResponseEntity<EntityModel<EquityTypeDto>> getEquityTypeById(@PathVariable final Long id) {
        EquityType equityType = equityTypeService.getEquityTypeById(id);
        EntityModel<EquityTypeDto> equityTypeEntityModel = toModel(modelMapper.map(equityType, EquityTypeDto.class));

        return ResponseEntity.ok().body(equityTypeEntityModel);
    }

    @PutMapping("/equityTypes/{id}")
    public ResponseEntity<EntityModel<EquityTypeDto>> replaceEquityType(
            @RequestBody final EquityTypeDto equityTypeDto,
            @PathVariable final Long id) {
        EquityType equityTypeRequest = modelMapper.map(equityTypeDto, EquityType.class);
        EquityType updatedEquityType = equityTypeService.updateEquityType(id, equityTypeRequest);
        EntityModel<EquityTypeDto> equityTypeEntityModel = toModel(modelMapper.map(updatedEquityType, EquityTypeDto.class));

        return ResponseEntity.ok().body(equityTypeEntityModel);

    }

    @DeleteMapping("/equityTypes/{id}")
    public ResponseEntity<String> deleteEquityType(@PathVariable final Long id) {
        equityTypeService.deleteEquityType(id);
        return ResponseEntity.ok().body("Equity type deleted successfully");
    }

    private EntityModel<EquityTypeDto> toModel(final EquityTypeDto assessmentType) {
        return EntityModel.of(assessmentType,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityTypeController.class).getEquityTypeById(assessmentType.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityTypeController.class).getAllEquityTypes()).withRel("equityTypes"));
    }

    private CollectionModel<EntityModel<EquityTypeDto>> toCollectionModel(final Iterable<? extends EquityTypeDto> assessmentTypes) {
        List<EntityModel<EquityTypeDto>> assessmentTypeList = StreamSupport.stream(assessmentTypes.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(assessmentTypeList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityTypeController.class)
                        .getAllEquityTypes())
                .withSelfRel());
    }
}
