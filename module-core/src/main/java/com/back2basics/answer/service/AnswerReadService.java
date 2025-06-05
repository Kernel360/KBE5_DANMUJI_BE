package com.back2basics.answer.service;

import com.back2basics.answer.port.in.AnswerReadUseCase;
import com.back2basics.answer.port.out.AnswerReadPort;
import com.back2basics.answer.service.result.AnswerReadResult;
import com.back2basics.infra.validation.validator.QuestionValidator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerReadService implements AnswerReadUseCase {

    private final QuestionValidator questionValidator;
    private final AnswerReadPort answerReadPort;

    @Override
    public List<AnswerReadResult> getAnswersByQuestionId(Long userId, Long questionId) {
        questionValidator.findById(questionId);

        return answerReadPort.findAllAnswersByQuestionId(questionId).stream()
            .map(AnswerReadResult::toResult)
            .collect(Collectors.toList());
    }

}
