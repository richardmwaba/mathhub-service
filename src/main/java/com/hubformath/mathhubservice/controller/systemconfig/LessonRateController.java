package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import com.hubformath.mathhubservice.service.systemconfig.LessonRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/v1/systemconfig/sis")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class LessonRateController {
    private final LessonRateService lessonRateService;

    @Autowired
    public LessonRateController(final LessonRateService lessonRateService) {
        this.lessonRateService = lessonRateService;
    }

    @GetMapping("/lessonRates")
    public ResponseEntity<CollectionModel<EntityModel<LessonRate>>> getAllLessonRates(@RequestParam final Optional<Instant> effectiveDate,
                                                                                      @RequestParam final Optional<Instant> expiryDate) {
        List<LessonRate> lessonRates = lessonRateService.getAllLessonRates(effectiveDate.orElse(null),
                                                                           expiryDate.orElse(null));
        CollectionModel<EntityModel<LessonRate>> lessonRateCollectionModel = toCollectionModel(lessonRates,
                                                                                               effectiveDate,
                                                                                               expiryDate);

        return ResponseEntity.ok().body(lessonRateCollectionModel);
    }

    @PostMapping("/lessonRates")
    public ResponseEntity<EntityModel<LessonRate>> newLessonRate(@RequestBody final LessonRate lessonRateRequest) {
        LessonRate newLessonRate = lessonRateService.createLessonRate(lessonRateRequest);
        EntityModel<LessonRate> lessonRateEntity = toModel(newLessonRate, Optional.empty(), Optional.empty());

        return ResponseEntity.created(lessonRateEntity.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                             body(lessonRateEntity);
    }

    @GetMapping("/lessonRates/{lessonRateId}")
    public ResponseEntity<EntityModel<LessonRate>> getLessonRateById(@PathVariable final String lessonRateId) {
        try {
            LessonRate lessonRate = lessonRateService.getLessonRateById(lessonRateId);
            EntityModel<LessonRate> lessonRateEntity = toModel(lessonRate, Optional.empty(), Optional.empty());
            return ResponseEntity.ok().body(lessonRateEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<LessonRate> toModel(final LessonRate lessonRate,
                                            final Optional<Instant> effectiveDate,
                                            final Optional<Instant> expiryDate) {
        return EntityModel.of(lessonRate,
                              linkTo(methodOn(LessonRateController.class).getLessonRateById(lessonRate.getLessonRateId())).withSelfRel(),
                              linkTo(methodOn(LessonRateController.class).getAllLessonRates(effectiveDate,
                                                                                            expiryDate)).withRel(
                                      "lessonRates"));
    }

    private CollectionModel<EntityModel<LessonRate>> toCollectionModel(final Iterable<? extends LessonRate> lessonRatesIterable,
                                                                       final Optional<Instant> effectiveDate,
                                                                       final Optional<Instant> expiryDate) {
        List<EntityModel<LessonRate>> lessonRates = StreamSupport.stream(lessonRatesIterable.spliterator(), false)
                                                                 .map(lessonRate -> this.toModel(lessonRate,
                                                                                                 effectiveDate,
                                                                                                 expiryDate))
                                                                 .toList();

        return CollectionModel.of(lessonRates, linkTo(methodOn(LessonRateController.class)
                                                              .getAllLessonRates(effectiveDate, expiryDate))
                .withSelfRel());
    }
}