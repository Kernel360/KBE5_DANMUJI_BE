package com.back2basics.question.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionDeleteCommand {

    private final Long postId;
    private final Long requesterId;

    @Builder
    public QuestionDeleteCommand(Long postId, Long requesterId) {
        this.postId = postId;
        this.requesterId = requesterId;
    }
}