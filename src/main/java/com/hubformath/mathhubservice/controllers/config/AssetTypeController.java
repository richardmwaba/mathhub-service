package com.hubformath.mathhubservice.controllers.config;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

import com.hubformath.mathhubservice.assemblers.config.AssetTypeModelAssembler;
import com.hubformath.mathhubservice.models.config.AssetType;
import com.hubformath.mathhubservice.repositories.config.AssetTypeRepository;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@RestController
@RequestMapping(path="/v1/api/ops")
public class AssetTypeController {
    @Autowired
    private final AssetTypeRepository repository;
    private final AssetTypeModelAssembler assembler;

    public AssetTypeController(AssetTypeRepository repository, AssetTypeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/assetTypes")
    public CollectionModel<EntityModel<AssetType>> all() {
        List<EntityModel<AssetType>> assetTypes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(assetTypes, linkTo(methodOn(AssetTypeController.class).all()).withSelfRel());
    }

    @PostMapping("/assetTypes")
    public ResponseEntity<EntityModel<AssetType>> newAssetType(@RequestBody AssetType newAssetType) {
        EntityModel<AssetType> entityModel = assembler.toModel(repository.save(newAssetType));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/assetTypes/{id}")
    public EntityModel<AssetType> one(@PathVariable Long id) {
        AssetType assetType = repository.findById(id) //
                .orElseThrow(() -> new ItemNotFoundException(id, "assetType"));

        return assembler.toModel(assetType);
    }

    @PutMapping("/assetTypes/{id}")
    public ResponseEntity<EntityModel<AssetType>> replaceAssetType(@RequestBody AssetType newAssetType,
            @PathVariable Long id) {
        AssetType updatedAssetType = repository.findById(id) //
                .map(assetType -> {
                    assetType.setTypeName(newAssetType.getTypeName());
                    assetType.setTypeDescription(newAssetType.getTypeDescription());
                    return repository.save(assetType);
                }) //
                .orElseGet(() -> {
                    newAssetType.setId(id);
                    return repository.save(newAssetType);
                });

        EntityModel<AssetType> entityModel = assembler.toModel(updatedAssetType);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/assetTypes/{id}")
    public ResponseEntity<?> deleteAssetType(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
