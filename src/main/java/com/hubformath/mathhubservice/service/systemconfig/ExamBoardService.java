package com.hubformath.mathhubservice.service.systemconfig;


import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;
import com.hubformath.mathhubservice.repository.systemconfig.ExamBoardRepository;
import com.hubformath.mathhubservice.util.exceptions.ItemNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamBoardService {

    private final ExamBoardRepository examBoardRepository;

    private final String notFoundItemName;

    public ExamBoardService(final ExamBoardRepository examBoardRepository) {
        this.examBoardRepository = examBoardRepository;
        this.notFoundItemName = "Exam Board";
    }

    public List<ExamBoard> getAllExamBoards() {
        return examBoardRepository.findAll();
    }

    public ExamBoard getExamBoardById(Long id) {
        Optional<ExamBoard> examBoard = examBoardRepository.findById(id);

        if(examBoard.isPresent()){
            return examBoard.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
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
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    public void deleteExamBoard(Long id) {
        ExamBoard examBoard = examBoardRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        examBoardRepository.delete(examBoard);
    }


}



