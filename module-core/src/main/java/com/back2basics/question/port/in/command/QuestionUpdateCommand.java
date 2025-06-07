package com.back2basics.question.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionUpdateCommand {
    
    private final String content;

    @Builder
    public QuestionUpdateCommand(String content) {
        this.content = content;
    }
}
