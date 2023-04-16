package com.hubformath.mathhubservice.service.systemconfig;


import com.hubformath.mathhubservice.model.systemconfig.Syllabus;
import com.hubformath.mathhubservice.repository.systemconfig.SyllabusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SyllabusService {

    private final SyllabusRepository syllabusRepository;

    public SyllabusService(SyllabusRepository syllabusRepository) {
        this.syllabusRepository = syllabusRepository;
    }

    public List<Syllabus> getAllSyllabi() {
        return syllabusRepository.findAll();
    }

    public Syllabus getSyllabusById(UUID syllabusId) {
        return syllabusRepository.findById(syllabusId).orElseThrow();
    }

    public Syllabus createSyllabus(Syllabus syllabusRequest) {
        return syllabusRepository.save(syllabusRequest);
    }

    public Syllabus updateSyllabus(UUID syllabusId, Syllabus syllabusRequest) {
        return syllabusRepository.findById(syllabusId)
                .map(syllabus -> {
                    Optional.ofNullable(syllabusRequest.getSyllabusName()).ifPresent(syllabus::setSyllabusName);
                    Optional.ofNullable(syllabusRequest.getSyllabusDescription()).ifPresent(syllabus::setSyllabusDescription);
                    return syllabusRepository.save(syllabus);
                })
                .orElseThrow();
    }

    public void deleteSyllabus(UUID syllabusId) {
        Syllabus syllabus = syllabusRepository.findById(syllabusId)
                .orElseThrow();

        syllabusRepository.delete(syllabus);
    }
}



