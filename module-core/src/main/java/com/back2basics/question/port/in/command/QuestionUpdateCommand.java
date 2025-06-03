package com.back2basics.question.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionUpdateCommand {

    private final Long requesterId;
    private final String content;

    @Builder
    public QuestionUpdateCommand(Long requesterId, String content) {
        this.requesterId = requesterId;
        this.content = content;
    }
}
