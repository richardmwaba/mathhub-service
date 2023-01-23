package com.hubformath.mathhubservice.services.config.impl;


import com.hubformath.mathhubservice.models.config.ExamBoard;
import com.hubformath.mathhubservice.repositories.config.ExamBoardRepository;
import com.hubformath.mathhubservice.services.config.IExamBoardService;
import com.hubformath.mathhubservice.utils.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamBoardServiceImpl implements IExamBoardService {
    private final ExamBoardRepository examBoardRepository;
    private final String notFoundItemName;

    public ExamBoardServiceImpl(ExamBoardRepository examBoardRepository) {
        super();
        this.examBoardRepository = examBoardRepository;
        this.notFoundItemName = "Exam Board";
    }

    @Override
    public List<ExamBoard> getAllExamBoards() {
        return examBoardRepository.findAll();
    }

    @Override
    public ExamBoard getExamBoardById(Long id) {
        Optional<ExamBoard> examBoard = examBoardRepository.findById(id);

        if(examBoard.isPresent()){
            return examBoard.get();
        } else {
            throw new ItemNotFoundException(id, notFoundItemName);
        }
    }

    @Override
    public ExamBoard createExamBoard(ExamBoard examBoardRequest) {
        return examBoardRepository.save(examBoardRequest);
    }

    @Override
    public ExamBoard updateExamBoard(Long id, ExamBoard examBoardRequest) {
        return examBoardRepository.findById(id)
                .map(examBoard -> {
                    examBoard.setExamBoardName(examBoardRequest.getExamBoardName());
                    examBoard.setExamBoardDescription(examBoardRequest.getExamBoardDescription());
                    return examBoardRepository.save(examBoard);
                })
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));
    }

    @Override
    public void deleteExamBoard(Long id) {
        ExamBoard examBoard = examBoardRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id, notFoundItemName));

        examBoardRepository.delete(examBoard);
    }


}



