package com.hubformath.mathhubservice.controllers.config;

import com.hubformath.mathhubservice.assemblers.config.SyllabusModelAssembler;
import com.hubformath.mathhubservice.dtos.config.SyllabusDto;
import com.hubformath.mathhubservice.models.config.Syllabus;
import com.hubformath.mathhubservice.services.config.ISyllabusService;
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
    public class SyllabusController {
        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private ISyllabusService syllabusService;

        @Autowired
        private SyllabusModelAssembler syllabusModelAssembler;

        public SyllabusController() {super();}

        @GetMapping("/syllabus")
        public ResponseEntity<CollectionModel<EntityModel<SyllabusDto>>> getAllSyllabi() {
            List<SyllabusDto> syllabus = syllabusService.getAllSyllabi().stream()
                    .map(syllabi -> modelMapper.map(syllabi, SyllabusDto.class))
                    .collect(Collectors.toList());

            CollectionModel<EntityModel<SyllabusDto>> syllabusCollectionModel = syllabusModelAssembler
                    .toCollectionModel(syllabus);

            return ResponseEntity.ok().body(syllabusCollectionModel);
        }

        @PostMapping("/syllabus")
        public ResponseEntity<EntityModel<SyllabusDto>> newSyllabus(
                @RequestBody SyllabusDto syllabusDto) {
            Syllabus syllabusRequest = modelMapper.map(syllabusDto, Syllabus.class);
            Syllabus newSyllabus = syllabusService.createSyllabus(syllabusRequest);

            EntityModel<SyllabusDto> syllabusEntityModel = syllabusModelAssembler
                    .toModel(modelMapper.map(newSyllabus, SyllabusDto.class));

            return ResponseEntity.created(syllabusEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(syllabusEntityModel);
        }

        @GetMapping("/syllabus/{id}")
        public ResponseEntity<EntityModel<SyllabusDto>> getSyllabusById(@PathVariable Long id) {
            Syllabus syllabus = syllabusService.getSyllabusById(id);

            EntityModel<SyllabusDto> syllabusEntityModel = syllabusModelAssembler
                    .toModel(modelMapper.map(syllabus, SyllabusDto.class));

            return ResponseEntity.ok().body(syllabusEntityModel);
        }

        @PutMapping("/syllabus/{id}")
        public ResponseEntity<EntityModel<SyllabusDto>> replaceSyllabus(
                @RequestBody SyllabusDto syllabusDto,
                @PathVariable Long id) {
            Syllabus syllabusRequest = modelMapper.map(syllabusDto, Syllabus.class);
            Syllabus updatedSyllabus = syllabusService.updateSyllabus(id, syllabusRequest);

            EntityModel<SyllabusDto> syllabusEntityModel = syllabusModelAssembler
                    .toModel(modelMapper.map(updatedSyllabus, SyllabusDto.class));

            return ResponseEntity.ok().body(syllabusEntityModel);

        }

        @DeleteMapping("/syllabus/{id}")
        public ResponseEntity<String> deleteSyllabus(@PathVariable Long id) {
            syllabusService.deleteSyllabus(id);

            return ResponseEntity.ok().body("Syllabus deleted successfully");
        }
}
