package com.hubformath.mathhubservice.controllers.config;

import com.hubformath.mathhubservice.assemblers.config.ExamBoardModelAssembler;
import com.hubformath.mathhubservice.dtos.config.ExamBoardDto;
import com.hubformath.mathhubservice.models.config.ExamBoard;
import com.hubformath.mathhubservice.services.config.IExamBoardService;
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
    @RequestMapping(path="/api/v1/ops")
    public class ExamBoardController {
        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private IExamBoardService examBoardService;

        @Autowired
        private ExamBoardModelAssembler examBoardModelAssembler;

        public ExamBoardController() {super();}

        @GetMapping("/examBoard")
        public ResponseEntity<CollectionModel<EntityModel<ExamBoardDto>>> getAllExamBoard() {
            List<ExamBoardDto> examBoardList = examBoardService.getAllExamBoards().stream()
                    .map(examBoard -> modelMapper.map(examBoard, ExamBoardDto.class))
                    .collect(Collectors.toList());

            CollectionModel<EntityModel<ExamBoardDto>> examBoardCollectionModel = examBoardModelAssembler
                    .toCollectionModel(examBoardList);

            return ResponseEntity.ok().body(examBoardCollectionModel);
        }

        @PostMapping("/examBoard")
        public ResponseEntity<EntityModel<ExamBoardDto>> newExamBoard(
                @RequestBody ExamBoardDto examBoardDto) {
            ExamBoard examBoardRequest = modelMapper.map(examBoardDto, ExamBoard.class);
            ExamBoard newExamBoard = examBoardService.createExamBoard(examBoardRequest);

            EntityModel<ExamBoardDto> examBoardEntityModel = examBoardModelAssembler
                    .toModel(modelMapper.map(newExamBoard, ExamBoardDto.class));

            return ResponseEntity.created(examBoardEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(examBoardEntityModel);
        }

        @GetMapping("/examBoard/{id}")
        public ResponseEntity<EntityModel<ExamBoardDto>> getExamBoardById(@PathVariable Long id) {
            ExamBoard examBoard = examBoardService.getExamBoardById(id);

            EntityModel<ExamBoardDto> examBoardEntityModel = examBoardModelAssembler
                    .toModel(modelMapper.map(examBoard, ExamBoardDto.class));

            return ResponseEntity.ok().body(examBoardEntityModel);
        }

        @PutMapping("/examBoard/{id}")
        public ResponseEntity<EntityModel<ExamBoardDto>> replaceExamBoard(
                @RequestBody ExamBoardDto examBoardDto,
                @PathVariable Long id) {
            ExamBoard examBoardRequest = modelMapper.map(examBoardDto, ExamBoard.class);
            ExamBoard updatedExamBoard = examBoardService.updateExamBoard(id, examBoardRequest);

            EntityModel<ExamBoardDto> examBoardEntityModel = examBoardModelAssembler
                    .toModel(modelMapper.map(updatedExamBoard, ExamBoardDto.class));

            return ResponseEntity.ok().body(examBoardEntityModel);

        }

        @DeleteMapping("/examBoard/{id}")
        public ResponseEntity<String> deleteExamBoard(@PathVariable Long id) {
            examBoardService.deleteExamBoard(id);

            return ResponseEntity.ok().body("Exam Board deleted successfully");
        }
}
