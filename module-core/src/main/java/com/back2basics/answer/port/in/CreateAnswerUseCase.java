package com.back2basics.answer.port.in;

import com.back2basics.answer.port.in.command.CreateAnswerCommand;

public interface CreateAnswerUseCase {

    Long createAnswer(Long inquiryId, Long authorId, CreateAnswerCommand command);
}
