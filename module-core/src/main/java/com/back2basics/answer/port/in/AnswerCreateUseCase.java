package com.back2basics.answer.port.in;

import com.back2basics.answer.port.in.command.AnswerCreateCommand;

public interface AnswerCreateUseCase {

    Long createAnswer(Long userId, AnswerCreateCommand command);
}
