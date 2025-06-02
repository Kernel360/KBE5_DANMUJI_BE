package com.back2basics.question.port.in;

import com.back2basics.question.service.result.QuestionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionReadUseCase {

    Page<QuestionResult> getAllQuestions(Pageable pageable);

    Page<QuestionResult> getQuestionsByPostId(Long postId, Pageable pageable);
}
