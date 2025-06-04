package com.back2basics.question.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionCreateCommand {

    private final Long postId;
    private final String content;

    @Builder
    public QuestionCreateCommand(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
