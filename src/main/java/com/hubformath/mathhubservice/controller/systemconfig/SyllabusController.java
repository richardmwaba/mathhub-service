package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.dto.systemconfig.SyllabusDto;
import com.hubformath.mathhubservice.model.systemconfig.Syllabus;
import com.hubformath.mathhubservice.service.systemconfig.ISyllabusService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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


@RestController
@RequestMapping(path="/api/v1/systemconfig/ops")
public class SyllabusController {

    private final ModelMapper modelMapper;

    private final ISyllabusService syllabusService;

    @Autowired
    public SyllabusController(ModelMapper modelMapper, ISyllabusService syllabusService) {
        this.modelMapper = modelMapper;
        this.syllabusService = syllabusService;
    }

    @GetMapping("/syllabus")
    public ResponseEntity<CollectionModel<EntityModel<SyllabusDto>>> getAllSyllabi() {
        List<SyllabusDto> syllabus = syllabusService.getAllSyllabi().stream()
                .map(syllabi -> modelMapper.map(syllabi, SyllabusDto.class))
                .toList();

        CollectionModel<EntityModel<SyllabusDto>> syllabusCollectionModel = toCollectionModel(syllabus);

        return ResponseEntity.ok().body(syllabusCollectionModel);
    }

    @PostMapping("/syllabus")
    public ResponseEntity<EntityModel<SyllabusDto>> newSyllabus(@RequestBody final SyllabusDto syllabusDto) {
        Syllabus syllabusRequest = modelMapper.map(syllabusDto, Syllabus.class);
        Syllabus newSyllabus = syllabusService.createSyllabus(syllabusRequest);
        EntityModel<SyllabusDto> syllabusEntityModel = toModel(modelMapper.map(newSyllabus, SyllabusDto.class));

        return ResponseEntity.created(syllabusEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(syllabusEntityModel);
    }

    @GetMapping("/syllabus/{id}")
    public ResponseEntity<EntityModel<SyllabusDto>> getSyllabusById(@PathVariable final Long id) {
        Syllabus syllabus = syllabusService.getSyllabusById(id);

        EntityModel<SyllabusDto> syllabusEntityModel = toModel(modelMapper.map(syllabus, SyllabusDto.class));

        return ResponseEntity.ok().body(syllabusEntityModel);
    }

    @PutMapping("/syllabus/{id}")
    public ResponseEntity<EntityModel<SyllabusDto>> replaceSyllabus(
            @RequestBody final SyllabusDto syllabusDto,
            @PathVariable final Long id) {
        Syllabus syllabusRequest = modelMapper.map(syllabusDto, Syllabus.class);
        Syllabus updatedSyllabus = syllabusService.updateSyllabus(id, syllabusRequest);

        EntityModel<SyllabusDto> syllabusEntityModel = toModel(modelMapper.map(updatedSyllabus, SyllabusDto.class));

        return ResponseEntity.ok().body(syllabusEntityModel);

    }

    @DeleteMapping("/syllabus/{id}")
    public ResponseEntity<String> deleteSyllabus(@PathVariable final Long id) {
        syllabusService.deleteSyllabus(id);
        return ResponseEntity.ok().body("Syllabus deleted successfully");
    }

    private EntityModel<SyllabusDto> toModel(final SyllabusDto syllabus) {
        return EntityModel.of(syllabus,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SyllabusController.class).getSyllabusById(syllabus.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SyllabusController.class).getAllSyllabi()).withRel("syllabus"));
    }

    private CollectionModel<EntityModel<SyllabusDto>> toCollectionModel(final Iterable<? extends SyllabusDto> syllabus) {
        List<EntityModel<SyllabusDto>> syllabusList = StreamSupport.stream(syllabus.spliterator(), false)
                .map(this::toModel)
                .toList();

        return CollectionModel.of(syllabusList, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SyllabusController.class)
                        .getAllSyllabi())
                .withSelfRel());
    }
}
