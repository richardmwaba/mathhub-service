package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.LiabilityType;
import com.hubformath.mathhubservice.service.systemconfig.LiabilityTypeService;
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
public class LiabilityTypeController {

    private final LiabilityTypeService liabilityTypeService;

    @Autowired
    public LiabilityTypeController(final LiabilityTypeService liabilityTypeService) {
        this.liabilityTypeService = liabilityTypeService;
    }

    @GetMapping("/liabilityTypes")
    public ResponseEntity<CollectionModel<?>> getAllLiabilityTypes() {
        List<LiabilityType> liabilityTypes = liabilityTypeService.getAllLiabilityTypes();
        return ResponseEntity.ok().body(toCollectionModel(liabilityTypes));
    }

    @PostMapping("/liabilityTypes")
    public ResponseEntity<EntityModel<LiabilityType>> newLiabilityType(@RequestBody LiabilityType liabilityTypeRequest) {
        EntityModel<LiabilityType> liabilityTypeEntityModel = toModel(liabilityTypeService.createLiabilityType(
                liabilityTypeRequest));
        return ResponseEntity.created(liabilityTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(liabilityTypeEntityModel);
    }

    @GetMapping("/liabilityTypes/{liabilityTypeId}")
    public ResponseEntity<EntityModel<LiabilityType>> getLiabilityTypeById(@PathVariable String liabilityTypeId) {
        try {
            EntityModel<LiabilityType> liabilityType = toModel(liabilityTypeService.getLiabilityTypeById(liabilityTypeId));
            return ResponseEntity.ok().body(liabilityType);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/liabilityTypes/{liabilityTypeId}")
    public ResponseEntity<EntityModel<LiabilityType>> replaceLiabilityType(@RequestBody LiabilityType liabilityTypeRequest,
                                                                           @PathVariable String liabilityTypeId) {
        try {
            EntityModel<LiabilityType> liabilityTypeEntityModel = toModel(liabilityTypeService.updateLiabilityType(
                    liabilityTypeId,
                    liabilityTypeRequest));
            return ResponseEntity.ok().body(liabilityTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/liabilityTypes/{liabilityTypeId}")
    public ResponseEntity<String> deleteLiabilityType(@PathVariable String liabilityTypeId) {
        try {
            liabilityTypeService.deleteLiabilityType(liabilityTypeId);
            return ResponseEntity.ok().body("Liability type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<LiabilityType> toModel(LiabilityType assessmentType) {
        return EntityModel.of(assessmentType,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityTypeController.class)
                                                                        .getLiabilityTypeById(assessmentType.getLiabilityTypeId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityTypeController.class)
                                                                        .getAllLiabilityTypes())
                                               .withRel("liabilityTypes"));
    }

    private CollectionModel<?> toCollectionModel(List<LiabilityType> liabilityTypeList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LiabilityTypeController.class)
                                                              .getAllLiabilityTypes()).withSelfRel();
        List<EntityModel<LiabilityType>> liabilityTypes = liabilityTypeList.stream()
                                                                           .map(this::toModel)
                                                                           .toList();

        return liabilityTypeList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(LiabilityType.class, link)
                : CollectionModel.of(liabilityTypes, link);
    }
}
