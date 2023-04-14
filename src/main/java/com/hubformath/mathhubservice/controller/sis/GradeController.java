package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.systemconfig.GradeDto;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.service.systemconfig.GradeService;
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
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(path = "/api/v1/sis")
public class GradeController {

    private final ModelMapper modelMapper;

    private final GradeService gradeService;

    @Autowired
    public GradeController(final ModelMapperConfig modelMapperConfig, final GradeService gradeService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.gradeService = gradeService;
    }

    @GetMapping("/grades")
    public ResponseEntity<CollectionModel<EntityModel<GradeDto>>> getAllGrades() {
        List<GradeDto> grades = gradeService.getAllGrades().stream().
                map(grade -> modelMapper.map(grade, GradeDto.class))
                .toList();
        CollectionModel<EntityModel<GradeDto>> gradeCollectionModel = toCollectionModel(grades);

        return ResponseEntity.ok().body(gradeCollectionModel);
    }

    @PostMapping("/grades")
    public ResponseEntity<EntityModel<GradeDto>> newGrade(@RequestBody final GradeDto gradeDto) {
        Grade gradeRequest = modelMapper.map(gradeDto, Grade.class);
        Grade newGrade = gradeService.createGrade(gradeRequest);
        EntityModel<GradeDto> gradeDtoEntityModel = toModel(modelMapper.map(newGrade, GradeDto.class));

        return ResponseEntity.created(gradeDtoEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(gradeDtoEntityModel);
    }

    @GetMapping("/grades/{gradeId}")
    public ResponseEntity<EntityModel<GradeDto>> getGradeById(@PathVariable final UUID gradeId) {
        try {
            Grade grade = gradeService.getGradeById(gradeId);
            EntityModel<GradeDto> gradeEntityModel = toModel(modelMapper.map(grade, GradeDto.class));
            return ResponseEntity.ok().body(gradeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/grades/{gradeId}")
    public ResponseEntity<EntityModel<GradeDto>> replaceGrade(@RequestBody final GradeDto gradeDto,
                                                              @PathVariable final UUID gradeId) {
        try {
            Grade gradeRequest = modelMapper.map(gradeDto, Grade.class);
            Grade updatedGrade = gradeService.updateGrade(gradeId, gradeRequest);
            EntityModel<GradeDto> gradeEntityModel = toModel(modelMapper.map(updatedGrade, GradeDto.class));
            return ResponseEntity.ok().body(gradeEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/grades/{gradeId}")
    public ResponseEntity<String> deleteGrade(@PathVariable final UUID gradeId) {
        try {
            gradeService.deleteGrade(gradeId);
            return ResponseEntity.ok().body("Grade deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<GradeDto> toModel(final GradeDto grade){
        return EntityModel.of(grade, linkTo(methodOn(GradeController.class).getGradeById(grade.getGradeId())).withSelfRel(),
                linkTo(methodOn(GradeController.class).getAllGrades()).withRel("grades"));
    }

    private CollectionModel<EntityModel<GradeDto>> toCollectionModel(final Iterable<? extends GradeDto> grades) {
        List<EntityModel<GradeDto>> gradeList = StreamSupport.stream(grades.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(gradeList, linkTo(methodOn(GradeController.class)
                .getAllGrades())
                .withSelfRel());
    }
}
