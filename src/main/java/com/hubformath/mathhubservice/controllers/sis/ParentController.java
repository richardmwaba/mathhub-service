package com.hubformath.mathhubservice.controllers.sis;

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

import com.hubformath.mathhubservice.assemblers.sis.ParentModelAssembler;
import com.hubformath.mathhubservice.dtos.sis.ParentDto;
import com.hubformath.mathhubservice.models.sis.Parent;
import com.hubformath.mathhubservice.services.sis.IParentService;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class ParentController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IParentService parentService;

    @Autowired
    private ParentModelAssembler parentModelAssembler;

    public ParentController() {
        super();
    }

    @GetMapping("/parents")
    public ResponseEntity<CollectionModel<EntityModel<ParentDto>>> getAllParents() {
        List<ParentDto> parents = parentService.getAllParents().stream()
                .map(parent -> modelMapper.map(parent, ParentDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ParentDto>> parentCollectionModel = parentModelAssembler
                .toCollectionModel(parents);

        return ResponseEntity.ok().body(parentCollectionModel);
    }

    @PostMapping("/parents")
    public ResponseEntity<EntityModel<ParentDto>> newParent(
            @RequestBody ParentDto parentDto) {
        Parent parentRequest = modelMapper.map(parentDto, Parent.class);
        Parent newParent = parentService.createParent(parentRequest);

        EntityModel<ParentDto> parentEntityModel = parentModelAssembler
                .toModel(modelMapper.map(newParent, ParentDto.class));

        return ResponseEntity.created(parentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(parentEntityModel);
    }

    @GetMapping("/parents/{id}")
    public ResponseEntity<EntityModel<ParentDto>> getParentById(@PathVariable Long id) {
        Parent parent = parentService.getParentById(id);

        EntityModel<ParentDto> parentEntityModel = parentModelAssembler
                .toModel(modelMapper.map(parent, ParentDto.class));

        return ResponseEntity.ok().body(parentEntityModel);
    }

    @PutMapping("/parents/{id}")
    public ResponseEntity<EntityModel<ParentDto>> replaceParent(
            @RequestBody ParentDto parentDto,
            @PathVariable Long id) {
        Parent parentRequest = modelMapper.map(parentDto, Parent.class);
        Parent updatedParent = parentService.updateParent(id, parentRequest);

        EntityModel<ParentDto> parentEntityModel = parentModelAssembler
                .toModel(modelMapper.map(updatedParent, ParentDto.class));

        return ResponseEntity.ok().body(parentEntityModel);

    }

    @DeleteMapping("/parents/{id}")
    public ResponseEntity<String> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);

        return ResponseEntity.ok().body("Parent deleted sucessfully");
    }
}
