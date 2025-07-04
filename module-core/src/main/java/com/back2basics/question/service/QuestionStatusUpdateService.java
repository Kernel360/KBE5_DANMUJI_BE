package com.back2basics.question.service;

import com.back2basics.infra.validator.QuestionValidator;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.in.QuestionStatusUpdateUseCase;
import com.back2basics.question.port.out.QuestionUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionStatusUpdateService implements QuestionStatusUpdateUseCase {

    private final QuestionValidator questionValidator;
    private final QuestionUpdatePort questionUpdatePort;

    @Override
    public void markAsAnswered(Long userId, String userIp, Long questionId) {
        Question question = questionValidator.findById(questionId);
        question.markAsAnswered();
        questionUpdatePort.update(question);
    }

    @Override
    public void markAsResolved(Long userId, String userIp, Long questionId) {
        Question question = questionValidator.findById(questionId);
        questionValidator.validateAuthor(question, userId);
        question.markAsResolved();
        questionUpdatePort.update(question);
    }
}
