package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.IncomeType;
import com.hubformath.mathhubservice.service.systemconfig.IncomeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RestController
@RequestMapping(path = "/api/v1/systemconfig/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class IncomeTypeController {

    private final IncomeTypeService incomeTypeService;

    @Autowired
    public IncomeTypeController(IncomeTypeService incomeTypeService) {
        this.incomeTypeService = incomeTypeService;
    }

    @GetMapping("/incomeTypes")
    public ResponseEntity<CollectionModel<?>> getAllIncomeTypes() {
        List<IncomeType> incomeTypes = incomeTypeService.getAllIncomeTypes();
        return ResponseEntity.ok().body(toCollectionModel(incomeTypes));
    }

    @PostMapping("/incomeTypes")
    public ResponseEntity<EntityModel<IncomeType>> newIncomeType(@RequestBody IncomeType incomeTypeRequest) {
        EntityModel<IncomeType> newIncomeType = toModel(incomeTypeService.createIncomeType(incomeTypeRequest));
        return ResponseEntity.created(newIncomeType.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(newIncomeType);
    }

    @GetMapping("/incomeTypes/{incomeTypeId}")
    public ResponseEntity<EntityModel<IncomeType>> getIncomeTypeById(@PathVariable String incomeTypeId) {
        try {
            EntityModel<IncomeType> incomeType = toModel(incomeTypeService.getIncomeTypeById(incomeTypeId));
            return ResponseEntity.ok().body(incomeType);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/incomeTypes/{incomeTypeId}")
    public ResponseEntity<EntityModel<IncomeType>> replaceIncomeType(@RequestBody IncomeType incomeTypeRequest,
                                                                     @PathVariable String incomeTypeId) {
        try {
            EntityModel<IncomeType> updatedIncomeType = toModel(incomeTypeService.updateIncomeType(incomeTypeId,
                                                                                                   incomeTypeRequest));
            return ResponseEntity.ok().body(updatedIncomeType);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/incomeTypes/{incomeTypeId}")
    public ResponseEntity<String> deleteIncomeType(@PathVariable String incomeTypeId) {
        try {
            incomeTypeService.deleteIncomeType(incomeTypeId);
            return ResponseEntity.ok().body("Income type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public EntityModel<IncomeType> toModel(IncomeType incomeType) {
        return EntityModel.of(incomeType,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeTypeController.class)
                                                                        .getIncomeTypeById(incomeType.getId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeTypeController.class)
                                                                        .getAllIncomeTypes()).withRel("incomeTypes"));
    }

    private CollectionModel<?> toCollectionModel(List<IncomeType> incomeTypeList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(IncomeTypeController.class)
                                                              .getAllIncomeTypes()).withSelfRel();
        List<EntityModel<IncomeType>> incomeTypes = incomeTypeList.stream()
                                                                  .map(this::toModel)
                                                                  .toList();

        return incomeTypeList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(IncomeType.class, link)
                : CollectionModel.of(incomeTypes, link);
    }
}
