package com.back2basics.infra.validation.validator;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.out.AnswerReadPort;
import com.back2basics.infra.exception.answer.AnswerErrorCode;
import com.back2basics.infra.exception.answer.AnswerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerValidator {

    private final AnswerReadPort answerRepository;

    public Answer findAnswerById(Long id) {
        return answerRepository.findById(id)
            .orElseThrow(() -> new AnswerException(AnswerErrorCode.ANSWER_NOT_FOUND));
    }

    public void isAuthor(Answer answer, Long requesterId) {
        if (!answer.getAuthorId().equals(requesterId)) {
            throw new AnswerException(AnswerErrorCode.INVALID_ANSWER_AUTHOR);
        }
    }
}
