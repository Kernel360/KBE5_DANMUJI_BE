package com.back2basics.question.port.in;

import com.back2basics.question.port.in.command.QuestionCreateCommand;

public interface QuestionCreateUseCase {

    Long create(Long userId, QuestionCreateCommand command);
}
