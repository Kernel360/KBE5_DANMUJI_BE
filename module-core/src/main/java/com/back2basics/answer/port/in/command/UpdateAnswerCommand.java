package com.back2basics.answer.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAnswerCommand {

    private String content;

}