package com.back2basics.answer.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerUpdateCommand {

    private Long requesterId;
    private String content;

}