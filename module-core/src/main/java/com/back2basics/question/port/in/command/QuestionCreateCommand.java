package com.back2basics.question.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionCreateCommand {

    private final Long postId;
    private final Long authorId;
    private final String content;

    @Builder
    public QuestionCreateCommand(Long postId, Long authorId, String content) {
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
    }
}
