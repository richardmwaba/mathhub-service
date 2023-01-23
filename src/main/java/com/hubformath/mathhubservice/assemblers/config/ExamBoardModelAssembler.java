package com.hubformath.mathhubservice.assemblers.config;

import com.hubformath.mathhubservice.controllers.config.ExamBoardController;
import com.hubformath.mathhubservice.dtos.config.ExamBoardDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ExamBoardModelAssembler implements RepresentationModelAssembler<ExamBoardDto, EntityModel<ExamBoardDto>> {
    @Override
    public EntityModel<ExamBoardDto> toModel(ExamBoardDto syllabus) {

        return EntityModel.of(syllabus,
                linkTo(methodOn(ExamBoardController.class).getExamBoardById(syllabus.getId())).withSelfRel(),
                linkTo(methodOn(ExamBoardController.class).getAllExamBoard()).withRel("examBoard"));
    }

    @Override
    public CollectionModel<EntityModel<ExamBoardDto>> toCollectionModel(
            Iterable<? extends ExamBoardDto> examBoard) {
        List<EntityModel<ExamBoardDto>> examBoardList = StreamSupport.stream(examBoard.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(examBoardList, linkTo(methodOn(ExamBoardController.class)
                .getAllExamBoard())
                .withSelfRel());
    }
}
