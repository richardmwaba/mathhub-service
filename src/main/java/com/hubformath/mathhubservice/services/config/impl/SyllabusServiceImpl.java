package com.hubformath.mathhubservice.services.config.impl;


import com.hubformath.mathhubservice.models.config.Syllabus;
import com.hubformath.mathhubservice.repositories.config.SyllabusRepository;
import com.hubformath.mathhubservice.services.config.ISyllabusService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SyllabusServiceImpl implements ISyllabusService {
    private final SyllabusRepository syllabusRepository;
    private final String notFoundItemName;

    public SyllabusServiceImpl(SyllabusRepository syllabusRepository) {
        super();
        this.syllabusRepository = syllabusRepository;
        this.notFoundItemName = "asset type";
    }

    @Override
    public List<Syllabus> getAllSyllabi() {
        return syllabusRepository.findAll();
    }

    @Override
    public Syllabus getSyllabusById(Long id) {
        Optional<Syllabus> syllabus = syllabusRepository.findById(id);

        if(syllabus.isPresent()){
            return syllabus.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public Syllabus createSyllabus(Syllabus syllabusRequest) {
        return syllabusRepository.save(syllabusRequest);
    }

    @Override
    public Syllabus updateSyllabus(Long id, Syllabus syllabusRequest) {
        return syllabusRepository.findById(id)
                .map(syllabus -> {
                    syllabus.setSyllabusName(syllabusRequest.getSyllabusName());
                    syllabus.setSyllabusDescription(syllabusRequest.getSyllabusDescription());
                    return syllabusRepository.save(syllabus);
                })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteSyllabus(Long id) {
        Syllabus syllabus = syllabusRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        syllabusRepository.delete(syllabus);
    }


}



