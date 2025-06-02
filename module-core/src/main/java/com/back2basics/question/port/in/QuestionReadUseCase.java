package com.back2basics.question.port.in;

import com.back2basics.question.service.result.QuestionResult;
import java.util.List;

public interface QuestionReadUseCase {

    List<QuestionResult> getQuestionsByPostId(Long postId);

    List<QuestionResult> getAllQuestions();
}
