package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.dto.systemconfig.SubjectDto;
import com.hubformath.mathhubservice.model.systemconfig.Subject;

import com.hubformath.mathhubservice.service.systemconfig.SubjectService;
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

import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(path = "/api/v1/sis")
public class SubjectController {

    private final ModelMapper modelMapper;

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(final ModelMapper modelMapper, final SubjectService subjectService) {
        this.modelMapper = modelMapper;
        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    public ResponseEntity<CollectionModel<EntityModel<SubjectDto>>> getAllSubjects() {
        List<SubjectDto> subjects = subjectService.getAllSubjects().stream().
                map(subject -> modelMapper.map(subject, SubjectDto.class))
                .toList();
        CollectionModel<EntityModel<SubjectDto>> subjectCollectionModel = toCollectionModel(subjects);

        return ResponseEntity.ok().body(subjectCollectionModel);
    }

    @PostMapping("/subjects")
    public ResponseEntity<EntityModel<SubjectDto>> newSubject(@RequestBody final SubjectDto subjectDto) {
        Subject newSubject = subjectService.createSubject(subjectDto);
        EntityModel<SubjectDto> subjectDtoEntityModel = toModel(modelMapper.map(newSubject, SubjectDto.class));

        return ResponseEntity.created(subjectDtoEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(subjectDtoEntityModel);
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<EntityModel<SubjectDto>> getSubjectById(@PathVariable final Long id) {
        Subject subject = subjectService.getSubjectById(id);
        EntityModel<SubjectDto> subjectEntityModel = toModel(modelMapper.map(subject, SubjectDto.class));

        return ResponseEntity.ok().body(subjectEntityModel);
    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<EntityModel<SubjectDto>> replaceSubject(
            @RequestBody final SubjectDto subjectDto,
            @PathVariable final Long id) {
        Subject subjectRequest = modelMapper.map(subjectDto, Subject.class);
        Subject updatedSubject = subjectService.updateSubject(id, subjectRequest);
        EntityModel<SubjectDto> subjectEntityModel = toModel(modelMapper.map(updatedSubject, SubjectDto.class));

        return ResponseEntity.ok().body(subjectEntityModel);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable final Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok().body("Subject deleted successfully");
    }

    private EntityModel<SubjectDto> toModel(final SubjectDto subject){
        return EntityModel.of(subject,
                linkTo(methodOn(SubjectController.class).getSubjectById(subject.getId())).withSelfRel(),
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
