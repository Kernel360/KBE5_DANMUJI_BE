package com.back2basics.answer.port.in;

import com.back2basics.answer.port.in.command.AnswerUpdateCommand;

public interface AnswerUpdateUseCase {

    void updateAnswer(Long id, AnswerUpdateCommand command);

}
