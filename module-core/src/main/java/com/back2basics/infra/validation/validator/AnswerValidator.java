package com.back2basics.infra.validation.validator;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.out.ReadAnswerPort;
import com.back2basics.infra.exception.answer.AnswerErrorCode;
import com.back2basics.infra.exception.answer.AnswerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerValidator {

    private final ReadAnswerPort answerRepository;

    public Answer findAnswerById(Long id) {
        return answerRepository.findById(id)
            .orElseThrow(() -> new AnswerException(AnswerErrorCode.ANSWER_NOT_FOUND));
    }

}
