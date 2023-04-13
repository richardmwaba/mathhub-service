package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.systemconfig.LessonRateDto;
import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import com.hubformath.mathhubservice.service.systemconfig.LessonRateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/v1/systemconfig/sis")
public class LessonRateController {
    private final ModelMapper modelMapper;

    private final LessonRateService lessonRateService;

    @Autowired
    public LessonRateController (final ModelMapperConfig modelMapperConfig, final LessonRateService lessonRateService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.lessonRateService = lessonRateService;
    }

    @GetMapping("/lessonRates")
    public ResponseEntity<CollectionModel<EntityModel<LessonRateDto>>> getAllLessonRates(@RequestParam final Optional<Instant> effectiveDate,
                                                                                         @RequestParam final Optional<Instant> expiryDate) {
        List<LessonRateDto> lessonRates = lessonRateService.getAllLessonRates(effectiveDate.orElse(null), expiryDate.orElse(null))
                .stream()
                .map(lessonRate -> modelMapper.map(lessonRate, LessonRateDto.class))
                .toList();
        CollectionModel<EntityModel<LessonRateDto>> lessonRateCollectionModel = toCollectionModel(lessonRates, effectiveDate, expiryDate);

        return ResponseEntity.ok().body(lessonRateCollectionModel);
    }

    @PostMapping("/lessonRates")
    public ResponseEntity<EntityModel<LessonRateDto>> newLessonRate(@RequestBody final LessonRateDto lessonRateDto) {
        LessonRate lessonRate = modelMapper.map(lessonRateDto, LessonRate.class);
        LessonRate newLessonRate = lessonRateService.createLessonRate(lessonRate);
        EntityModel<LessonRateDto> lessonRateDtoEntityModel =
                toModel(modelMapper.map(newLessonRate, LessonRateDto.class), Optional.empty(), Optional.empty());

        return ResponseEntity.created(lessonRateDtoEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(lessonRateDtoEntityModel);
    }

    @GetMapping("/lessonRates/{lessonRateId}")
    public ResponseEntity<EntityModel<LessonRateDto>> getLessonRateById(@PathVariable final UUID lessonRateId) {
        try {
            LessonRate lessonRate = lessonRateService.getLessonRateById(lessonRateId);
            EntityModel<LessonRateDto> lessonRateEntityModel =
                    toModel(modelMapper.map(lessonRate, LessonRateDto.class), Optional.empty(), Optional.empty());
            return ResponseEntity.ok().body(lessonRateEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<LessonRateDto> toModel(final LessonRateDto lessonRate, final Optional<Instant> effectiveDate, final Optional<Instant> expiryDate) {
        return EntityModel.of(lessonRate,
                linkTo(methodOn(LessonRateController.class).getLessonRateById(lessonRate.getLessonRateId())).withSelfRel(),
                linkTo(methodOn(LessonRateController.class).getAllLessonRates(effectiveDate, expiryDate)).withRel("lessonRates"));
    }

    private CollectionModel<EntityModel<LessonRateDto>> toCollectionModel(final Iterable<? extends LessonRateDto> lessonRates, final Optional<Instant> effectiveDate, final Optional<Instant> expiryDate) {
        List<EntityModel<LessonRateDto>> lessonRateList = StreamSupport.stream(lessonRates.spliterator(), false)
                .map(lessonRate -> this.toModel(lessonRate, effectiveDate, expiryDate))
                .toList();

        return CollectionModel.of(lessonRateList, linkTo(methodOn(LessonRateController.class)
                .getAllLessonRates(effectiveDate, expiryDate))
                .withSelfRel());
    }
}