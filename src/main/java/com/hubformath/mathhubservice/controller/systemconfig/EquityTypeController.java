package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.EquityType;
import com.hubformath.mathhubservice.service.systemconfig.EquityTypeService;
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
public class EquityTypeController {

    private final EquityTypeService equityTypeService;

    @Autowired
    public EquityTypeController(final EquityTypeService equityTypeService) {
        this.equityTypeService = equityTypeService;
    }

    @GetMapping("/equityTypes")
    public ResponseEntity<CollectionModel<?>> getAllEquityTypes() {
        List<EquityType> equityTypes = equityTypeService.getAllEquityTypes().stream().toList();
        return ResponseEntity.ok().body(toCollectionModel(equityTypes));
    }

    @PostMapping("/equityTypes")
    public ResponseEntity<EntityModel<EquityType>> newEquityType(@RequestBody EquityType equityTypeRequest) {
        EquityType newEquityType = equityTypeService.createEquityType(equityTypeRequest);
        EntityModel<EquityType> equityTypeEntityModel = toModel(newEquityType);

        return ResponseEntity.created(equityTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(equityTypeEntityModel);
    }

    @GetMapping("/equityTypes/{equityTypeId}")
    public ResponseEntity<EntityModel<EquityType>> getEquityTypeById(@PathVariable String equityTypeId) {
        try {
            EquityType equityType = equityTypeService.getEquityTypeById(equityTypeId);
            EntityModel<EquityType> equityTypeEntityModel = toModel(equityType);

            return ResponseEntity.ok().body(equityTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/equityTypes/{equityTypeId}")
    public ResponseEntity<EntityModel<EquityType>> replaceEquityType(@RequestBody EquityType equityTypeRequest,
                                                                     @PathVariable String equityTypeId) {
        try {
            EquityType updatedEquityType = equityTypeService.updateEquityType(equityTypeId, equityTypeRequest);
            EntityModel<EquityType> equityTypeEntityModel = toModel(updatedEquityType);

            return ResponseEntity.ok().body(equityTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/equityTypes/{equityTypeId}")
    public ResponseEntity<String> deleteEquityType(@PathVariable String equityTypeId) {
        try {
            equityTypeService.deleteEquityType(equityTypeId);
            return ResponseEntity.ok().body("Equity type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<EquityType> toModel(EquityType assessmentType) {
        return EntityModel.of(assessmentType,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityTypeController.class)
                                                                        .getEquityTypeById(assessmentType.getId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityTypeController.class)
                                                                        .getAllEquityTypes()).withRel("equityTypes"));
    }

    private CollectionModel<?> toCollectionModel(List<EquityType> equityTypesList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EquityTypeController.class)
                                                              .getAllEquityTypes()).withSelfRel();
        List<EntityModel<EquityType>> equityTypes = equityTypesList.stream()
                                                                     .map(this::toModel)
                                                                     .toList();

        return equityTypesList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(EquityType.class, link)
                : CollectionModel.of(equityTypes, link);
    }
}
