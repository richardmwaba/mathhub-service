package com.hubformath.mathhubservice.controllers.config;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

import com.hubformath.mathhubservice.assemblers.config.IncomeTypeModelAssembler;
import com.hubformath.mathhubservice.models.config.IncomeType;
import com.hubformath.mathhubservice.repositories.config.IncomeTypeRepository;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;

@RestController
@RequestMapping(path="/v1/api/ops")
public class IncomeTypeController {
    private final IncomeTypeRepository repository;
    private final IncomeTypeModelAssembler assembler;

    public IncomeTypeController(IncomeTypeRepository repository, IncomeTypeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/incomeTypes")
    public CollectionModel<EntityModel<IncomeType>> all() {
        List<EntityModel<IncomeType>> incomeTypes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(incomeTypes, linkTo(methodOn(IncomeTypeController.class).all()).withSelfRel());
    }

    @PostMapping("/incomeTypes")
    public ResponseEntity<EntityModel<IncomeType>> newIncomeType(@RequestBody IncomeType newIncomeType) {
        EntityModel<IncomeType> entityModel = assembler.toModel(repository.save(newIncomeType));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/incomeTypes/{id}")
    public EntityModel<IncomeType> one(@PathVariable Long id) {
        IncomeType incomeType = repository.findById(id) //
                .orElseThrow(() -> new ItemNotFoundException(id, "incomeType"));

        return assembler.toModel(incomeType);
    }

    @PutMapping("/incomeTypes/{id}")
    public ResponseEntity<EntityModel<IncomeType>> replaceIncomeType(@RequestBody IncomeType newIncomeType,
            @PathVariable Long id) {
        IncomeType updatedIncomeType = repository.findById(id) //
                .map(incomeType -> {
                    incomeType.setTypeName(newIncomeType.getTypeName());
                    incomeType.setTypeDescription(newIncomeType.getTypeDescription());
                    return repository.save(incomeType);
                }) //
                .orElseGet(() -> {
                    newIncomeType.setId(id);
                    return repository.save(newIncomeType);
                });

        EntityModel<IncomeType> entityModel = assembler.toModel(updatedIncomeType);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/incomeTypes/{id}")
    public ResponseEntity<?> deleteIncomeType(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
