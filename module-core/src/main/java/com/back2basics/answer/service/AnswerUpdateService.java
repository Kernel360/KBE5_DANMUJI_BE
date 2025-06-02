package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.in.AnswerUpdateUseCase;
import com.back2basics.answer.port.in.command.AnswerUpdateCommand;
import com.back2basics.answer.port.out.AnswerUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerUpdateService implements AnswerUpdateUseCase {

    private final AnswerValidator answerValidator;
    private final AnswerUpdatePort answerUpdatePort;


    @Override
    public void updateAnswer(Long id, AnswerUpdateCommand command) {
        Answer answer = answerValidator.findComment(id);
        answerValidator.isAuthor(answer, command.getRequesterId());

        answer.update(command);
        answerUpdatePort.update(answer);
    }
}
