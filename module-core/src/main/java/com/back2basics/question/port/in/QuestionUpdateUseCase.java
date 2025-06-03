package com.back2basics.question.port.in;

import com.back2basics.question.port.in.command.QuestionUpdateCommand;

public interface QuestionUpdateUseCase {

    void update(Long questionId, QuestionUpdateCommand command);
}


