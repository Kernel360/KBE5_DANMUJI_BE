package com.back2basics.domain.answer.dto.request;

import com.back2basics.answer.port.in.command.AnswerDeleteCommand;
import lombok.Getter;

@Getter
public class AnswerDeleteRequest {

    private Long requesterId;

    public AnswerDeleteCommand toCommand() {
        return AnswerDeleteCommand.builder()
            .requesterId(requesterId)
            .build();
    }
}


