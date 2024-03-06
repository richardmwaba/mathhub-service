package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.systemconfig.ExamBoardDto;
import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.service.systemconfig.ExamBoardService;
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
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(path = "/api/v1/sis")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
public class ExamBoardController {

    private final ModelMapper modelMapper;

    private final ExamBoardService examBoardService;

    @Autowired
    public ExamBoardController(final ModelMapperConfig modelMapperConfig, final ExamBoardService examBoardService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.examBoardService = examBoardService;
    }

    @GetMapping("/examBoards")
    public ResponseEntity<CollectionModel<EntityModel<ExamBoardDto>>> getAllExamBoard() {
        List<ExamBoardDto> examBoardList = examBoardService.getAllExamBoards().stream()
                                                           .map(examBoard -> modelMapper.map(examBoard,
                                                                                             ExamBoardDto.class))
                                                           .toList();

        CollectionModel<EntityModel<ExamBoardDto>> examBoardCollectionModel = toCollectionModel(examBoardList);

        return ResponseEntity.ok().body(examBoardCollectionModel);
    }

    @PostMapping("/examBoards")
    public ResponseEntity<EntityModel<ExamBoardDto>> newExamBoard(@RequestBody final ExamBoardDto examBoardDto) {
        ExamBoard examBoardRequest = modelMapper.map(examBoardDto, ExamBoard.class);
        ExamBoard newExamBoard = examBoardService.createExamBoard(examBoardRequest);
        EntityModel<ExamBoardDto> examBoardEntityModel = toModel(modelMapper.map(newExamBoard, ExamBoardDto.class));

        return ResponseEntity.created(examBoardEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(examBoardEntityModel);
    }

    @GetMapping("/examBoards/{examBoardId}")
    public ResponseEntity<EntityModel<ExamBoardDto>> getExamBoardById(@PathVariable final String examBoardId) {
        try {
            ExamBoard examBoard = examBoardService.getExamBoardById(examBoardId);
            EntityModel<ExamBoardDto> examBoardEntityModel = toModel(modelMapper.map(examBoard, ExamBoardDto.class));
            return ResponseEntity.ok().body(examBoardEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/examBoards/{examBoardId}")
    public ResponseEntity<EntityModel<ExamBoardDto>> replaceExamBoard(@RequestBody final ExamBoardDto examBoardDto,
                                                                      @PathVariable final String examBoardId) {
        try {
            ExamBoard examBoardRequest = modelMapper.map(examBoardDto, ExamBoard.class);
            ExamBoard updatedExamBoard = examBoardService.updateExamBoard(examBoardId, examBoardRequest);
            EntityModel<ExamBoardDto> examBoardEntityModel = toModel(modelMapper.map(updatedExamBoard,
                                                                                     ExamBoardDto.class));
            return ResponseEntity.ok().body(examBoardEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/examBoards/{examBoardId}")
    public ResponseEntity<String> deleteExamBoard(@PathVariable final String examBoardId) {
        try {
            examBoardService.deleteExamBoard(examBoardId);
            return ResponseEntity.ok().body("Exam Board deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<ExamBoardDto> toModel(final ExamBoardDto syllabus) {
        return EntityModel.of(syllabus,
                              linkTo(methodOn(ExamBoardController.class).getExamBoardById(syllabus.getExamBoardId())).withSelfRel(),
                              linkTo(methodOn(ExamBoardController.class).getAllExamBoard()).withRel("examBoard"));
    }

    private CollectionModel<EntityModel<ExamBoardDto>> toCollectionModel(final Iterable<? extends ExamBoardDto> examBoard) {
        List<EntityModel<ExamBoardDto>> examBoardList = StreamSupport.stream(examBoard.spliterator(), false)
                                                                     .map(this::toModel)
                                                                     .toList();

        return CollectionModel.of(examBoardList, linkTo(methodOn(ExamBoardController.class)
                                                                .getAllExamBoard())
                .withSelfRel());
    }
}
