package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.service.systemconfig.ExamBoardService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(path = "/api/v1/sis")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('TEACHER')")
public class ExamBoardController {

    private final ExamBoardService examBoardService;

    @Autowired
    public ExamBoardController(ExamBoardService examBoardService) {
        this.examBoardService = examBoardService;
    }

    @GetMapping("/examBoards")
    public ResponseEntity<CollectionModel<?>> getAllExamBoard() {
        List<ExamBoard> examBoards = examBoardService.getAllExamBoards();
        return ResponseEntity.ok().body(toCollectionModel(examBoards));
    }

    @PostMapping("/examBoards")
    public ResponseEntity<EntityModel<ExamBoard>> newExamBoard(@RequestBody ExamBoard examBoardRequest) {
        EntityModel<ExamBoard> newExamBoard = toModel(examBoardService.createExamBoard(examBoardRequest));

        return ResponseEntity.created(newExamBoard.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(newExamBoard);
    }

    @GetMapping("/examBoards/{examBoardId}")
    public ResponseEntity<EntityModel<ExamBoard>> getExamBoardById(@PathVariable String examBoardId) {
        try {
            EntityModel<ExamBoard> examBoard = toModel(examBoardService.getExamBoardById(examBoardId));
            return ResponseEntity.ok().body(examBoard);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/examBoards/{examBoardId}")
    public ResponseEntity<EntityModel<ExamBoard>> replaceExamBoard(@RequestBody ExamBoard examBoardRequest,
                                                                   @PathVariable String examBoardId) {
        try {
            EntityModel<ExamBoard> updatedExamBoard = toModel(examBoardService.updateExamBoard(examBoardId,
                                                                                               examBoardRequest));
            return ResponseEntity.ok().body(updatedExamBoard);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/examBoards/{examBoardId}")
    public ResponseEntity<String> deleteExamBoard(@PathVariable String examBoardId) {
        try {
            examBoardService.deleteExamBoard(examBoardId);
            return ResponseEntity.ok().body("Exam Board deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<ExamBoard> toModel(ExamBoard examBoard) {
        return EntityModel.of(examBoard,
                              linkTo(methodOn(ExamBoardController.class).getExamBoardById(examBoard.getId())).withSelfRel(),
                              linkTo(methodOn(ExamBoardController.class).getAllExamBoard()).withRel("examBoard"));
    }

    private CollectionModel<?> toCollectionModel(List<ExamBoard> examBoardList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ExamBoardController.class)
                                                              .getAllExamBoard()).withSelfRel();
        List<EntityModel<ExamBoard>> examBoards = examBoardList.stream()
                                                               .map(this::toModel)
                                                               .toList();

        return examBoardList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(ExamBoard.class, link)
                : CollectionModel.of(examBoards, link);
    }
}
