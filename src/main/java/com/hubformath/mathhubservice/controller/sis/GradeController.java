package com.hubformath.mathhubservice.controller.sis;

import com.hubformath.mathhubservice.assembler.systemconfig.GradeModelAssembler;
import com.hubformath.mathhubservice.dto.systemconfig.GradeDto;
import com.hubformath.mathhubservice.model.systemconfig.Grade;
import com.hubformath.mathhubservice.service.systemconfig.IGradeService;

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


@RestController
@RequestMapping(path = "/api/v1/sis")
public class GradeController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IGradeService gradeService;

    @Autowired
    private GradeModelAssembler gradeModelAssembler;

    public GradeController() {super();}

    @GetMapping("/grades")
    public ResponseEntity<CollectionModel<EntityModel<GradeDto>>> getAllGrades() {
        List<GradeDto> grades = gradeService.getAllGrades().stream().
                map(grade -> modelMapper.map(grade, GradeDto.class))
                .toList();

        CollectionModel<EntityModel<GradeDto>> gradeCollectionModel = gradeModelAssembler
                .toCollectionModel(grades);

        return ResponseEntity.ok().body(gradeCollectionModel);
    }

    @PostMapping("/grades")
    public ResponseEntity<EntityModel<GradeDto>> newGrade(
            @RequestBody GradeDto gradeDto) {
        Grade gradeRequest = modelMapper.map(gradeDto, Grade.class);
        Grade newGrade = gradeService.createGrade(gradeRequest);

        EntityModel<GradeDto> gradeDtoEntityModel = gradeModelAssembler.
                toModel(modelMapper.map(newGrade, GradeDto.class));

        return ResponseEntity.created(gradeDtoEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(gradeDtoEntityModel);
    }

    @GetMapping("/grades/{id}")
    public ResponseEntity<EntityModel<GradeDto>> getGradeById(@PathVariable Long id) {
        Grade grade = gradeService.getGradeById(id);

        EntityModel<GradeDto> gradeEntityModel = gradeModelAssembler
                .toModel(modelMapper.map(grade, GradeDto.class));

        return ResponseEntity.ok().body(gradeEntityModel);
    }

    @PutMapping("/grades/{id}")
    public ResponseEntity<EntityModel<GradeDto>> replaceGrade(
            @RequestBody GradeDto gradeDto,
            @PathVariable Long id) {
        Grade gradeRequest = modelMapper.map(gradeDto, Grade.class);
        Grade updatedGrade = gradeService.updateGrade(id, gradeRequest);

        EntityModel<GradeDto> gradeEntityModel = gradeModelAssembler
                .toModel(modelMapper.map(updatedGrade, GradeDto.class));

        return ResponseEntity.ok().body(gradeEntityModel);
    }

    @DeleteMapping("/grades/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);

        return ResponseEntity.ok().body("Grade deleted successfully");
    }
}   
