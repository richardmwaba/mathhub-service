package com.hubformath.mathhubservice.service.systemconfig;


import com.hubformath.mathhubservice.model.systemconfig.Syllabus;
import com.hubformath.mathhubservice.repository.systemconfig.SyllabusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyllabusService {

    private final SyllabusRepository syllabusRepository;

    public SyllabusService(SyllabusRepository syllabusRepository) {
        this.syllabusRepository = syllabusRepository;
    }

    public List<Syllabus> getAllSyllabi() {
        return syllabusRepository.findAll();
    }

    public Syllabus getSyllabusById(Long id) {
        return syllabusRepository.findById(id).orElseThrow();
    }

    public Syllabus createSyllabus(Syllabus syllabusRequest) {
        return syllabusRepository.save(syllabusRequest);
    }

    public Syllabus updateSyllabus(Long id, Syllabus syllabusRequest) {
        return syllabusRepository.findById(id)
                .map(syllabus -> {
                    syllabus.setSyllabusName(syllabusRequest.getSyllabusName());
                    syllabus.setSyllabusDescription(syllabusRequest.getSyllabusDescription());
                    return syllabusRepository.save(syllabus);
                })
                .orElseThrow();
    }

    public void deleteSyllabus(Long id) {
        Syllabus syllabus = syllabusRepository.findById(id)
                .orElseThrow();

        syllabusRepository.delete(syllabus);
    }


}



