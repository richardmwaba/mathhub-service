package com.hubformath.mathhubservice.service.systemconfig;


import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.repository.systemconfig.ExamBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamBoardService {

    private final ExamBoardRepository examBoardRepository;

    public ExamBoardService(final ExamBoardRepository examBoardRepository) {
        this.examBoardRepository = examBoardRepository;
    }

    public List<ExamBoard> getAllExamBoards() {
        return examBoardRepository.findAll();
    }

    public ExamBoard getExamBoardById(Long id) {
        return examBoardRepository.findById(id).orElseThrow();
    }

    public ExamBoard createExamBoard(ExamBoard examBoardRequest) {
        return examBoardRepository.save(examBoardRequest);
    }

    public ExamBoard updateExamBoard(Long id, ExamBoard examBoardRequest) {
        return examBoardRepository.findById(id)
                .map(examBoard -> {
                    examBoard.setExamBoardName(examBoardRequest.getExamBoardName());
                    examBoard.setExamBoardDescription(examBoardRequest.getExamBoardDescription());
                    return examBoardRepository.save(examBoard);
                })
                .orElseThrow();
    }

    public void deleteExamBoard(Long id) {
        ExamBoard examBoard = examBoardRepository.findById(id)
                .orElseThrow();

        examBoardRepository.delete(examBoard);
    }


}



