package com.hubformath.mathhubservice.services.config;

import com.hubformath.mathhubservice.models.config.ExamBoard;

import java.util.List;

public interface IExamBoardService {

    List<ExamBoard> getAllExamBoards();

    ExamBoard createExamBoard(ExamBoard examBoard);

    ExamBoard getExamBoardById(Long id);

    ExamBoard updateExamBoard(Long  id, ExamBoard examBoard);

    void deleteExamBoard(Long id);
}
