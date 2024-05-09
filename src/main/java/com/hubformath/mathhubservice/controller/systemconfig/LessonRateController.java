package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.LessonRate;
import com.hubformath.mathhubservice.service.systemconfig.LessonRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public ResponseEntity<CollectionModel<?>> getAllLessonRates(@RequestParam Optional<Instant> effectiveDate,
                                                                @RequestParam Optional<Instant> expiryDate) {
        List<LessonRate> lessonRates = lessonRateService.getAllLessonRates(effectiveDate.orElse(null),
                                                                           expiryDate.orElse(null));
        return ResponseEntity.ok().body(toCollectionModel(lessonRates,
                                                          effectiveDate,
                                                          expiryDate));
    }

    @PostMapping("/lessonRates")
    public ResponseEntity<EntityModel<LessonRate>> newLessonRate(@RequestBody LessonRate lessonRateRequest) {
        LessonRate newLessonRate = lessonRateService.createLessonRate(lessonRateRequest);
        EntityModel<LessonRate> lessonRateEntity = toModel(newLessonRate, Optional.empty(), Optional.empty());

        return ResponseEntity.created(lessonRateEntity.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                             body(lessonRateEntity);
    }

    @GetMapping("/lessonRates/{lessonRateId}")
    public ResponseEntity<EntityModel<LessonRate>> getLessonRateById(@PathVariable String lessonRateId) {
        try {
            LessonRate lessonRate = lessonRateService.getLessonRateById(lessonRateId);
            EntityModel<LessonRate> lessonRateEntity = toModel(lessonRate, Optional.empty(), Optional.empty());
            return ResponseEntity.ok().body(lessonRateEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/lessonRates/{lessonRateId}")
    public ResponseEntity<EntityModel<LessonRate>> editLessonRate(@PathVariable String lessonRateId,
                                                                  @RequestBody LessonRate lessonRateRequest) {
        try {
            LessonRate lessonRate = lessonRateService.updateLessonRate(lessonRateId, lessonRateRequest);
            EntityModel<LessonRate> lessonRateEntity = toModel(lessonRate, Optional.empty(), Optional.empty());
            return ResponseEntity.ok().body(lessonRateEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<LessonRate> toModel(LessonRate lessonRate,
                                            Optional<Instant> effectiveDate,
                                            Optional<Instant> expiryDate) {
        return EntityModel.of(lessonRate,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LessonRateController.class)
                                                                        .getLessonRateById(lessonRate.getLessonRateId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LessonRateController.class)
                                                                        .getAllLessonRates(effectiveDate,
                                                                                           expiryDate)).withRel(
                                      "lessonRates"));
    }

    private CollectionModel<?> toCollectionModel(List<LessonRate> lessonRateList,
                                                 Optional<Instant> effectiveDate,
                                                 Optional<Instant> expiryDate) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LessonRateController.class)
                                                              .getAllLessonRates(effectiveDate, expiryDate))
                                     .withSelfRel();
        List<EntityModel<LessonRate>> lessonRates = lessonRateList.stream()
                                                                  .map(lessonRate -> this.toModel(lessonRate,
                                                                                                  effectiveDate,
                                                                                                  expiryDate)).toList();

        return lessonRateList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(LessonRate.class, link)
                : CollectionModel.of(lessonRates, link);
    }
}