package com.back2basics.question.service;

import com.back2basics.infra.validator.QuestionValidator;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.in.QuestionDeleteUseCase;
import com.back2basics.question.port.out.QuestionDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionDeleteService implements QuestionDeleteUseCase {

    private final QuestionValidator questionValidator;
    private final QuestionDeletePort questionDeletePort;

    @Override
    public void delete(Long userId, Long questionId) {
        Question question = questionValidator.findById(questionId);
        questionValidator.validateAuthor(question, userId);

        question.markAsDeleted();
        questionDeletePort.delete(questionId);
    }
}
