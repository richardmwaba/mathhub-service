package com.hubformath.mathhubservice.controllers.sis;

import com.hubformath.mathhubservice.assemblers.config.SubjectModelAssembler;
import com.hubformath.mathhubservice.dtos.config.SubjectDto;
import com.hubformath.mathhubservice.dtos.config.SubjectRequestDto;
import com.hubformath.mathhubservice.models.config.Subject;
import com.hubformath.mathhubservice.services.config.ISubjectService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/sis")
public class SubjectController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private SubjectModelAssembler subjectModelAssembler;

    public SubjectController(){ super(); }

    @GetMapping("/subjects")
    public ResponseEntity<CollectionModel<EntityModel<SubjectDto>>> getAllSubjects() {
        List<SubjectDto> subjects = subjectService.getAllSubjects().stream().
                map(subject -> modelMapper.map(subject, SubjectDto.class))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<SubjectDto>> subjectCollectionModel = subjectModelAssembler
                .toCollectionModel(subjects);

        return ResponseEntity.ok().body(subjectCollectionModel);
    }

    @PostMapping("/subjects")
    public ResponseEntity<EntityModel<SubjectDto>> newSubject(
            @RequestBody SubjectRequestDto subjectRequestDto) {
        Subject newSubject = subjectService.createSubject(subjectRequestDto);

        EntityModel<SubjectDto> subjectDtoEntityModel = subjectModelAssembler.
                toModel(modelMapper.map(newSubject, SubjectDto.class));

        return ResponseEntity.created(subjectDtoEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(subjectDtoEntityModel);
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<EntityModel<SubjectDto>> getSubjectById(@PathVariable Long id) {
        Subject subject = subjectService.getSubjectById(id);

        EntityModel<SubjectDto> subjectEntityModel = subjectModelAssembler
                .toModel(modelMapper.map(subject, SubjectDto.class));

        return ResponseEntity.ok().body(subjectEntityModel);
    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<EntityModel<SubjectDto>> replaceSubject(
            @RequestBody SubjectDto subjectDto,
            @PathVariable Long id) {
        Subject subjectRequest = modelMapper.map(subjectDto, Subject.class);
        Subject updatedSubject = subjectService.updateSubject(id, subjectRequest);

        EntityModel<SubjectDto> subjectEntityModel = subjectModelAssembler
                .toModel(modelMapper.map(updatedSubject, SubjectDto.class));

        return ResponseEntity.ok().body(subjectEntityModel);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);

        return ResponseEntity.ok().body("Subject deleted successfully");
    }
}
