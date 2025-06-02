package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.in.AnswerDeleteUseCase;
import com.back2basics.answer.port.out.AnswerDeletePort;
import com.back2basics.infra.validation.validator.AnswerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerDeleteService implements AnswerDeleteUseCase {

    private final AnswerValidator answerValidator;
    private final AnswerDeletePort answerDeletePort;

    @Override
    public void deleteAnswer(Long id, Long requesterId) {
        Answer answer = answerValidator.findAnswerById(id);
        answerValidator.isAuthor(answer, requesterId);

        answerDeletePort.delete(id);
    }
}
