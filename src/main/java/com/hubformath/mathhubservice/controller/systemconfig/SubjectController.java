package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.systemconfig.SubjectDto;
import com.hubformath.mathhubservice.model.systemconfig.Subject;
import com.hubformath.mathhubservice.service.systemconfig.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(path = "/api/v1/sis")
public class SubjectController {

    private final ModelMapper modelMapper;

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(final ModelMapperConfig modelMapperConfig, final SubjectService subjectService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER') or hasRole('TEACHER')")
    public ResponseEntity<CollectionModel<EntityModel<SubjectDto>>> getAllSubjects() {
        List<SubjectDto> subjects = subjectService.getAllSubjects().stream().
                                                  map(subject -> modelMapper.map(subject, SubjectDto.class))
                                                  .toList();
        CollectionModel<EntityModel<SubjectDto>> subjectCollectionModel = toCollectionModel(subjects);

        return ResponseEntity.ok().body(subjectCollectionModel);
    }

    @PostMapping("/subjects")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    public ResponseEntity<EntityModel<SubjectDto>> newSubject(@RequestBody final SubjectDto subjectDto) {
        Subject newSubject = subjectService.createSubject(subjectDto);
        EntityModel<SubjectDto> subjectDtoEntityModel = toModel(modelMapper.map(newSubject, SubjectDto.class));

        return ResponseEntity.created(subjectDtoEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                             body(subjectDtoEntityModel);
    }

    @GetMapping("/subjects/{subjectId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER') or hasRole('TEACHER')")
    public ResponseEntity<EntityModel<SubjectDto>> getSubjectById(@PathVariable final UUID subjectId) {
        try {
            Subject subject = subjectService.getSubjectById(subjectId);
            EntityModel<SubjectDto> subjectEntityModel = toModel(modelMapper.map(subject, SubjectDto.class));
            return ResponseEntity.ok().body(subjectEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/subjects/{subjectId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    public ResponseEntity<EntityModel<SubjectDto>> replaceSubject(@RequestBody final SubjectDto subjectDto,
                                                                  @PathVariable final UUID subjectId) {
        try {
            Subject subjectRequest = modelMapper.map(subjectDto, Subject.class);
            Subject updatedSubject = subjectService.updateSubject(subjectId, subjectRequest);
            EntityModel<SubjectDto> subjectEntityModel = toModel(modelMapper.map(updatedSubject, SubjectDto.class));
            return ResponseEntity.ok().body(subjectEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/subjects/{subjectId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
    public ResponseEntity<String> deleteSubject(@PathVariable final UUID subjectId) {
        try {
            subjectService.deleteSubject(subjectId);
            return ResponseEntity.ok().body("Subject deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<SubjectDto> toModel(final SubjectDto subject) {
        return EntityModel.of(subject,
                              linkTo(methodOn(SubjectController.class).getSubjectById(subject.getSubjectId())).withSelfRel(),
                              linkTo(methodOn(SubjectController.class).getAllSubjects()).withRel("subjects"));
    }

    private CollectionModel<EntityModel<SubjectDto>> toCollectionModel(final Iterable<? extends SubjectDto> subjects) {
        List<EntityModel<SubjectDto>> subjectList = StreamSupport.stream(subjects.spliterator(), false)
                                                                 .map(this::toModel)
                                                                 .toList();

        return CollectionModel.of(subjectList, linkTo(methodOn(SubjectController.class)
                                                              .getAllSubjects())
                .withSelfRel());
    }
}
