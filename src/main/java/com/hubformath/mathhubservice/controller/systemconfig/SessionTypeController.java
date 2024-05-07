package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.SessionType;
import com.hubformath.mathhubservice.service.systemconfig.SessionTypeService;
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
@RequestMapping(path = "/api/v1/systemconfig/sis")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER') or hasRole('TEACHER')")
public class SessionTypeController {

    private final SessionTypeService sessionTypeService;

    @Autowired
    public SessionTypeController(final SessionTypeService sessionTypeService) {
        this.sessionTypeService = sessionTypeService;
    }

    @GetMapping("/sessionTypes")
    public ResponseEntity<CollectionModel<?>> getAllSessionTypes() {
        List<SessionType> sessionTypes = sessionTypeService.getAllSessionTypes();
        return ResponseEntity.ok().body(toCollectionModel(sessionTypes));
    }

    @PostMapping("/sessionTypes")
    public ResponseEntity<EntityModel<SessionType>> newSessionType(@RequestBody SessionType sessionTypeRequest) {
        EntityModel<SessionType> newSessionType = toModel(sessionTypeService.createSessionType(sessionTypeRequest));
        return ResponseEntity.created(newSessionType.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(newSessionType);
    }

    @GetMapping("/sessionTypes/{sessionTypeId}")
    public ResponseEntity<EntityModel<SessionType>> getSessionTypeById(@PathVariable String sessionTypeId) {
        try {
            EntityModel<SessionType> sessionType = toModel(sessionTypeService.getSessionTypeById(sessionTypeId));
            return ResponseEntity.ok().body(sessionType);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/sessionTypes/{sessionTypeId}")
    public ResponseEntity<EntityModel<SessionType>> replaceSessionType(@RequestBody SessionType sessionTypeRequest,
                                                                       @PathVariable String sessionTypeId) {
        try {
            EntityModel<SessionType> updatedSessionType = toModel(sessionTypeService.updateSessionType(sessionTypeId,
                                                                                                       sessionTypeRequest));
            return ResponseEntity.ok().body(updatedSessionType);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/sessionTypes/{sessionTypeId}")
    public ResponseEntity<String> deleteSessionType(@PathVariable String sessionTypeId) {
        try {
            sessionTypeService.deleteSessionType(sessionTypeId);
            return ResponseEntity.ok().body("Session type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<SessionType> toModel(SessionType sessionType) {
        return EntityModel.of(sessionType,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SessionTypeController.class)
                                                                        .getSessionTypeById(sessionType.getSessionTypeId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SessionTypeController.class)
                                                                        .getAllSessionTypes()).withRel("sessionTypes"));
    }

    private CollectionModel<?> toCollectionModel(List<SessionType> sessionTypeList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SessionTypeController.class)
                                                              .getAllSessionTypes()).withSelfRel();
        List<EntityModel<SessionType>> sessionTypes = sessionTypeList.stream()
                                                                     .map(this::toModel)
                                                                     .toList();


        return sessionTypeList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(SessionType.class, link)
                : CollectionModel.of(sessionTypes, link);
    }
}
