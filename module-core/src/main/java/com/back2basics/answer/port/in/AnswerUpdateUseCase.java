package com.back2basics.answer.port.in;

import com.back2basics.answer.port.in.command.AnswerUpdateCommand;

public interface AnswerUpdateUseCase {

    void updateAnswer(Long userId, String userIp, Long answerId, AnswerUpdateCommand command);

}
