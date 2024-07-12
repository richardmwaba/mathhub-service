package com.hubformath.mathhubservice.controller.systemconfig;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.systemconfig.ClassRate;
import com.hubformath.mathhubservice.service.systemconfig.ClassRateService;
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
public class ClassRateController {
    private final ClassRateService classRateService;

    @Autowired
    public ClassRateController(final ClassRateService classRateService) {
        this.classRateService = classRateService;
    }

    @GetMapping("/classRates")
    public ResponseEntity<CollectionModel<?>> getAllClassRates(@RequestParam Optional<Instant> effectiveDate,
                                                               @RequestParam Optional<Instant> expiryDate) {
        List<ClassRate> classRates = classRateService.getAllClassRates(effectiveDate.orElse(null),
                                                                       expiryDate.orElse(null));
        return ResponseEntity.ok().body(toCollectionModel(classRates,
                                                          effectiveDate,
                                                          expiryDate));
    }

    @PostMapping("/classRates")
    public ResponseEntity<EntityModel<ClassRate>> createClassRate(@RequestBody ClassRate classRateRequest) {
        ClassRate newClassRate = classRateService.createClassRate(classRateRequest);
        EntityModel<ClassRate> classRateEntity = toModel(newClassRate, Optional.empty(), Optional.empty());

        return ResponseEntity.created(classRateEntity.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                             body(classRateEntity);
    }

    @GetMapping("/classRates/{classRateId}")
    public ResponseEntity<EntityModel<ClassRate>> getClassRateById(@PathVariable String classRateId) {
        try {
            ClassRate classRate = classRateService.getClassRateById(classRateId);
            EntityModel<ClassRate> classRateEntity = toModel(classRate, Optional.empty(), Optional.empty());
            return ResponseEntity.ok().body(classRateEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/classRates/{classRateId}")
    public ResponseEntity<EntityModel<ClassRate>> editClassRate(@PathVariable String classRateId,
                                                                @RequestBody ClassRate classRateRequest) {
        try {
            ClassRate classRate = classRateService.updateClassRate(classRateId, classRateRequest);
            EntityModel<ClassRate> classRateEntity = toModel(classRate, Optional.empty(), Optional.empty());
            return ResponseEntity.ok().body(classRateEntity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<ClassRate> toModel(ClassRate classRate,
                                           Optional<Instant> effectiveDate,
                                           Optional<Instant> expiryDate) {
        return EntityModel.of(classRate,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClassRateController.class)
                                                                        .getClassRateById(classRate.getId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClassRateController.class)
                                                                        .getAllClassRates(effectiveDate,
                                                                                          expiryDate)).withRel(
                                      "classRates"));
    }

    private CollectionModel<?> toCollectionModel(List<ClassRate> classRateList,
                                                 Optional<Instant> effectiveDate,
                                                 Optional<Instant> expiryDate) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClassRateController.class)
                                                              .getAllClassRates(effectiveDate, expiryDate))
                                     .withSelfRel();
        List<EntityModel<ClassRate>> classRates = classRateList.stream()
                                                                .map(lessonRate -> this.toModel(lessonRate,
                                                                                                effectiveDate,
                                                                                                expiryDate)).toList();

        return classRateList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(ClassRate.class, link)
                : CollectionModel.of(classRates, link);
    }
}