package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.systemconfig.SessionTypeDto;
import com.hubformath.mathhubservice.model.systemconfig.SessionType;
import com.hubformath.mathhubservice.service.systemconfig.SessionTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
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
import java.util.UUID;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/systemconfig/sis")
@PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER') or hasRole('TEACHER')")
public class SessionTypeController {

    private final ModelMapper modelMapper;

    private final SessionTypeService sessionTypeService;

    @Autowired
    public SessionTypeController(final ModelMapperConfig modelMapperConfig,
                                 final SessionTypeService sessionTypeService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.sessionTypeService = sessionTypeService;
    }

    @GetMapping("/sessionTypes")
    public ResponseEntity<CollectionModel<EntityModel<SessionTypeDto>>> getAllSessionTypes() {
        List<SessionTypeDto> sessionTypes = sessionTypeService.getAllSessionTypes().stream()
                                                              .map(sessionType -> modelMapper.map(sessionType,
                                                                                                  SessionTypeDto.class))
                                                              .toList();

        CollectionModel<EntityModel<SessionTypeDto>> sessionTypeCollectionModel = toCollectionModel(sessionTypes);

        return ResponseEntity.ok().body(sessionTypeCollectionModel);
    }

    @PostMapping("/sessionTypes")
    public ResponseEntity<EntityModel<SessionTypeDto>> newSessionType(@RequestBody final SessionTypeDto sessionTypeDto) {
        SessionType sessionTypeRequest = modelMapper.map(sessionTypeDto, SessionType.class);
        SessionType newSessionType = sessionTypeService.createSessionType(sessionTypeRequest);
        EntityModel<SessionTypeDto> sessionTypeEntityModel = toModel(modelMapper.map(newSessionType,
                                                                                     SessionTypeDto.class));

        return ResponseEntity.created(sessionTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(sessionTypeEntityModel);
    }

    @GetMapping("/sessionTypes/{sessionTypeId}")
    public ResponseEntity<EntityModel<SessionTypeDto>> getSessionTypeById(@PathVariable final UUID sessionTypeId) {
        try {
            SessionType sessionType = sessionTypeService.getSessionTypeById(sessionTypeId);
            EntityModel<SessionTypeDto> sessionTypeEntityModel = toModel(modelMapper.map(sessionType,
                                                                                         SessionTypeDto.class));
            return ResponseEntity.ok().body(sessionTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/sessionTypes/{sessionTypeId}")
    public ResponseEntity<EntityModel<SessionTypeDto>> replaceSessionType(@RequestBody final SessionTypeDto sessionTypeDto,
                                                                          @PathVariable final UUID sessionTypeId) {
        try {
            SessionType sessionTypeRequest = modelMapper.map(sessionTypeDto, SessionType.class);
            SessionType updatedSessionType = sessionTypeService.updateSessionType(sessionTypeId, sessionTypeRequest);
            EntityModel<SessionTypeDto> sessionTypeEntityModel = toModel(modelMapper.map(updatedSessionType,
                                                                                         SessionTypeDto.class));
            return ResponseEntity.ok().body(sessionTypeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/sessionTypes/{sessionTypeId}")
    public ResponseEntity<String> deleteSessionType(@PathVariable final UUID sessionTypeId) {
        try {
            sessionTypeService.deleteSessionType(sessionTypeId);
            return ResponseEntity.ok().body("Session type deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<SessionTypeDto> toModel(final SessionTypeDto sessionType) {
        return EntityModel.of(sessionType,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SessionTypeController.class)
                                                                        .getSessionTypeById(sessionType.getSessionTypeId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SessionTypeController.class)
                                                                        .getAllSessionTypes()).withRel("sessionTypes"));
    }

    private CollectionModel<EntityModel<SessionTypeDto>> toCollectionModel(final Iterable<? extends SessionTypeDto> sessionTypes) {
        List<EntityModel<SessionTypeDto>> sessionTypeList = StreamSupport.stream(sessionTypes.spliterator(), false)
                                                                         .map(this::toModel)
                                                                         .toList();

        return CollectionModel.of(sessionTypeList,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SessionTypeController.class)
                                                                            .getAllSessionTypes())
                                                   .withSelfRel());
    }
}
