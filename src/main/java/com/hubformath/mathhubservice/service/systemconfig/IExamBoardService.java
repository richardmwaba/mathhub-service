package com.hubformath.mathhubservice.service.systemconfig;

import java.util.List;

import com.hubformath.mathhubservice.model.systemconfig.ExamBoard;

public interface IExamBoardService {

    List<ExamBoard> getAllExamBoards();

    ExamBoard createExamBoard(ExamBoard examBoard);

    ExamBoard getExamBoardById(Long id);

    ExamBoard updateExamBoard(Long  id, ExamBoard examBoard);

    void deleteExamBoard(Long id);
}
