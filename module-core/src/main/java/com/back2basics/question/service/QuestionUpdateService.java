package com.back2basics.question.service;

import com.back2basics.infra.validator.QuestionValidator;
import com.back2basics.question.model.Question;
import com.back2basics.question.port.in.QuestionUpdateUseCase;
import com.back2basics.question.port.in.command.QuestionUpdateCommand;
import com.back2basics.question.port.out.QuestionUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionUpdateService implements QuestionUpdateUseCase {

    private final QuestionValidator questionValidator;
    private final QuestionUpdatePort questionUpdatePort;

    @Override
    public void update(Long userId, String userIp, Long questionId, QuestionUpdateCommand command) {
        Question question = questionValidator.findById(questionId);
        questionValidator.validateAuthor(question, userId);

        question.updateContent(command.getContent(), userIp);
        questionUpdatePort.update(question);
    }
}
