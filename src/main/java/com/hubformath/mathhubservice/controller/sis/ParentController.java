package com.hubformath.mathhubservice.controller.sis;

import java.util.List;
import java.util.stream.StreamSupport;

import com.hubformath.mathhubservice.service.sis.ParentService;
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

import com.hubformath.mathhubservice.dto.sis.ParentDto;
import com.hubformath.mathhubservice.model.sis.Parent;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class ParentController {

    private final ModelMapper modelMapper;

    private final ParentService parentService;

    @Autowired
    public ParentController(final ModelMapper modelMapper, final ParentService parentService) {
        this.modelMapper = modelMapper;
        this.parentService = parentService;
    }

    @GetMapping("/parents")
    public ResponseEntity<CollectionModel<EntityModel<ParentDto>>> getAllParents() {
        List<ParentDto> parents = parentService.getAllParents().stream()
                .map(parent -> modelMapper.map(parent, ParentDto.class))
                .toList();
        CollectionModel<EntityModel<ParentDto>> parentCollectionModel = toCollectionModel(parents);

        return ResponseEntity.ok().body(parentCollectionModel);
    }

    @PostMapping("/parents")
    public ResponseEntity<EntityModel<ParentDto>> newParent(@RequestBody final ParentDto parentDto) {
        Parent parentRequest = modelMapper.map(parentDto, Parent.class);
        Parent newParent = parentService.createParent(parentRequest);
        EntityModel<ParentDto> parentEntityModel = toModel(modelMapper.map(newParent, ParentDto.class));

        return ResponseEntity.created(parentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(parentEntityModel);
    }

    @GetMapping("/parents/{id}")
    public ResponseEntity<EntityModel<ParentDto>> getParentById(@PathVariable final Long id) {
        Parent parent = parentService.getParentById(id);
        EntityModel<ParentDto> parentEntityModel = toModel(modelMapper.map(parent, ParentDto.class));

        return ResponseEntity.ok().body(parentEntityModel);
    }

    @PutMapping("/parents/{id}")
    public ResponseEntity<EntityModel<ParentDto>> replaceParent(
            @RequestBody final ParentDto parentDto,
            @PathVariable final Long id) {
        Parent parentRequest = modelMapper.map(parentDto, Parent.class);
        Parent updatedParent = parentService.updateParent(id, parentRequest);

        EntityModel<ParentDto> parentEntityModel = toModel(modelMapper.map(updatedParent, ParentDto.class));

        return ResponseEntity.ok().body(parentEntityModel);

    }

    @DeleteMapping("/parents/{id}")
    public ResponseEntity<String> deleteParent(@PathVariable final Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.ok().body("Parent deleted successfully");
    }

    public EntityModel<ParentDto> toModel(final ParentDto parent) {
        return EntityModel.of(parent,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class).getParentById(parent.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class).getAllParents()).withRel("parents"));
    }

    private CollectionModel<EntityModel<ParentDto>> toCollectionModel(final Iterable<? extends ParentDto> parents) {
        List<EntityModel<ParentDto>> parentList = StreamSupport.stream(parents.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(parentList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ParentController.class)
                        .getAllParents())
                .withSelfRel());
    }
}
