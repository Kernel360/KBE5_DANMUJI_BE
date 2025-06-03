package com.back2basics.answer.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerCreateCommand {

    private Long questionId;
    private Long parentId;
    private Long authorId;
    private String content;
}
