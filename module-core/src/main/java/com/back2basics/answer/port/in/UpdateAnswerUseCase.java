package com.back2basics.answer.port.in;

import com.back2basics.answer.port.in.command.UpdateAnswerCommand;

public interface UpdateAnswerUseCase {

    void updateAnswer(Long userId, Long answerId, UpdateAnswerCommand command);

}
