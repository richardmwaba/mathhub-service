package com.hubformath.mathhubservice.controllers.config;

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

import com.hubformath.mathhubservice.assemblers.config.SessionTypeModelAssembler;
import com.hubformath.mathhubservice.dtos.config.SessionTypeDto;
import com.hubformath.mathhubservice.models.config.SessionType;
import com.hubformath.mathhubservice.services.config.ISessionTypeService;

@RestController
@RequestMapping(path="/v1/sis/ops")
public class SessionTypeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ISessionTypeService sessionTypeService;

    @Autowired
    private SessionTypeModelAssembler sessionTypeModelAssembler;

    public SessionTypeController() {
        super();
    }

    @GetMapping("/sessionTypes")
    public ResponseEntity<CollectionModel<EntityModel<SessionTypeDto>>> getAllSessionTypes() {
        List<SessionTypeDto> sessionTypes = sessionTypeService.getAllSessionTypes().stream()
                .map(sessionType -> modelMapper.map(sessionType, SessionTypeDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<SessionTypeDto>> sessionTypeCollectionModel = sessionTypeModelAssembler
                .toCollectionModel(sessionTypes);

        return ResponseEntity.ok().body(sessionTypeCollectionModel);
    }

    @PostMapping("/sessionTypes")
    public ResponseEntity<EntityModel<SessionTypeDto>> newSessionType(
            @RequestBody SessionTypeDto sessionTypeDto) {
        SessionType sessionTypeRequest = modelMapper.map(sessionTypeDto, SessionType.class);
        SessionType newSessionType = sessionTypeService.createSessionType(sessionTypeRequest);

        EntityModel<SessionTypeDto> sessionTypeEntityModel = sessionTypeModelAssembler
                .toModel(modelMapper.map(newSessionType, SessionTypeDto.class));

        return ResponseEntity.created(sessionTypeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(sessionTypeEntityModel);
    }

    @GetMapping("/sessionTypes/{id}")
    public ResponseEntity<EntityModel<SessionTypeDto>> getSessionTypeById(@PathVariable Long id) {
        SessionType sessionType = sessionTypeService.getSessionTypeById(id);

        EntityModel<SessionTypeDto> sessionTypeEntityModel = sessionTypeModelAssembler
                .toModel(modelMapper.map(sessionType, SessionTypeDto.class));

        return ResponseEntity.ok().body(sessionTypeEntityModel);
    }

    @PutMapping("/sessionTypes/{id}")
    public ResponseEntity<EntityModel<SessionTypeDto>> replaceSessionType(
            @RequestBody SessionTypeDto sessionTypeDto,
            @PathVariable Long id) {
        SessionType sessionTypeRequest = modelMapper.map(sessionTypeDto, SessionType.class);
        SessionType updatedSessionType = sessionTypeService.updateSessionType(id, sessionTypeRequest);

        EntityModel<SessionTypeDto> sessionTypeEntityModel = sessionTypeModelAssembler
                .toModel(modelMapper.map(updatedSessionType, SessionTypeDto.class));

        return ResponseEntity.ok().body(sessionTypeEntityModel);

    }

    @DeleteMapping("/sessionTypes/{id}")
    public ResponseEntity<String> deleteSessionType(@PathVariable Long id) {
        sessionTypeService.deleteSessionType(id);

        return ResponseEntity.ok().body("Session type deleted successfully");
    }
}
