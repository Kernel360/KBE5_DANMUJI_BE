package com.back2basics.infra.validation.validator;


import com.back2basics.infra.exception.question.QuestionErrorCode;
import com.back2basics.infra.exception.question.QuestionException;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.out.QuestionReadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionValidator {

    private final QuestionReadPort questionLoadPort;

    public Question findById(Long id) {
        return questionLoadPort.findById(id)
            .orElseThrow(() -> new QuestionException(QuestionErrorCode.QUESTION_NOT_FOUND));
    }

    public void validateAuthor(Question question, Long requesterId) {
        if (!question.getAuthorId().equals(requesterId)) {
            throw new QuestionException(QuestionErrorCode.INVALID_QUESTION_AUTHOR);
        }
    }
}