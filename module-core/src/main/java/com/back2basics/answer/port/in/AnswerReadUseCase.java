package com.back2basics.answer.port.in;

import com.back2basics.answer.service.result.AnswerReadResult;
import java.util.List;

public interface AnswerReadUseCase {

    List<AnswerReadResult> getAnswersByQuestionId(Long userId, Long questionId);
}
