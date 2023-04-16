package com.hubformath.mathhubservice.service.systemconfig;


import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.repository.systemconfig.ExamBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExamBoardService {

    private final ExamBoardRepository examBoardRepository;

    public ExamBoardService(final ExamBoardRepository examBoardRepository) {
        this.examBoardRepository = examBoardRepository;
    }

    public List<ExamBoard> getAllExamBoards() {
        return examBoardRepository.findAll();
    }

    public ExamBoard getExamBoardById(UUID examBoardId) {
        return examBoardRepository.findById(examBoardId).orElseThrow();
    }

    public ExamBoard createExamBoard(ExamBoard examBoardRequest) {
        return examBoardRepository.save(examBoardRequest);
    }

    public ExamBoard updateExamBoard(UUID examBoardId, ExamBoard examBoardRequest) {
        return examBoardRepository.findById(examBoardId)
                .map(examBoard -> {
                    Optional.ofNullable(examBoardRequest.getExamBoardName()).ifPresent(examBoard::setExamBoardName);
                    Optional.ofNullable(examBoardRequest.getExamBoardDescription()).ifPresent(examBoard::setExamBoardDescription);
                    return examBoardRepository.save(examBoard);
                })
                .orElseThrow();
    }

    public void deleteExamBoard(UUID examBoardId) {
        ExamBoard examBoard = examBoardRepository.findById(examBoardId)
                .orElseThrow();

        examBoardRepository.delete(examBoard);
    }


}



