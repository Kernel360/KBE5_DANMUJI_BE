package com.back2basics.question.port.in;

import com.back2basics.question.service.result.QuestionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionReadUseCase {

    Page<QuestionResult> getAllQuestions(Long userId, Pageable pageable);

    Page<QuestionResult> getQuestionsByPostId(Long userId, Long postId, Pageable pageable);

    QuestionResult getQuestionById(Long userId, Long questionId);
}
