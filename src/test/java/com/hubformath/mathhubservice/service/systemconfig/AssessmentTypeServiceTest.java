package com.hubformath.mathhubservice.service.systemconfig;

import com.hubformath.mathhubservice.model.systemconfig.AssessmentType;
import com.hubformath.mathhubservice.repository.systemconfig.AssessmentTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssessmentTypeServiceTest {

    @Mock
    private AssessmentTypeRepository assessmentTypeRepository;

    private AssessmentTypeService assessmentTypeService;

    @BeforeEach
    void setup() {
        assessmentTypeService = new AssessmentTypeService(assessmentTypeRepository);
    }

    @Test
    void shouldReturnAllAssessmentTypes() {
        when(assessmentTypeRepository.findAll()).thenReturn(getAssessmentTypes());

        assertThat(assessmentTypeService.getAllAssessmentTypes()).isEqualTo(getAssessmentTypes());
    }

    @Test
    void shouldReturnAssessmentTypeById() {
        AssessmentType assessmentType = getAssessmentTypes().get(0);
        when(assessmentTypeRepository.findById("1")).thenReturn(Optional.ofNullable(assessmentType));

        assertThat(assessmentTypeService.getAssessmentTypeById("1")).isEqualTo(assessmentType);
    }

    @Test
    void shouldCreateAssessmentType() {
        AssessmentType expectedAssessmentType = new AssessmentType();
        expectedAssessmentType.setId(UUID.randomUUID().toString());
        expectedAssessmentType.setName("Test");
        expectedAssessmentType.setDescription("End of term test");
        when(assessmentTypeRepository.save(expectedAssessmentType)).thenReturn(expectedAssessmentType);

        AssessmentType actualAssessmentType = assessmentTypeService.createAssessmentType(expectedAssessmentType);

        verify(assessmentTypeRepository).save(expectedAssessmentType);
        assertThat(actualAssessmentType).isEqualTo(expectedAssessmentType);
    }

    @Test
    void shouldUpdateAssessmentType() {
        AssessmentType existingAssessmentType = getAssessmentTypes().get(0);
        AssessmentType updatedAssessmentType = new AssessmentType("1", "Quiz", "Pop quiz");
        when(assessmentTypeRepository.findById("1")).thenReturn(Optional.of(existingAssessmentType));
        when(assessmentTypeRepository.save(existingAssessmentType)).thenReturn(updatedAssessmentType);

        AssessmentType actualAssessmentType = assessmentTypeService.updateAssessmentType("1", updatedAssessmentType);

        verify(assessmentTypeRepository).save(existingAssessmentType);
        assertThat(actualAssessmentType).isEqualTo(updatedAssessmentType);
    }

    @Test
    void shouldDeleteAssessmentType() {
        AssessmentType assessmentType = getAssessmentTypes().get(0);
        when(assessmentTypeRepository.findById("1")).thenReturn(Optional.of(assessmentType));

        assessmentTypeService.deleteAssessmentType("1");

        verify(assessmentTypeRepository).delete(assessmentType);
    }

    private List<AssessmentType> getAssessmentTypes() {
        return List.of(new AssessmentType("1", "Test", "End of term test"),
                       new AssessmentType("2", "Quiz", "Pop quiz"));
    }
}