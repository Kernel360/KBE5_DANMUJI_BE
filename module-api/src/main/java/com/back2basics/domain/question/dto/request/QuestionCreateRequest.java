package com.back2basics.domain.question.dto.request;

import com.back2basics.question.port.in.command.QuestionCreateCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class QuestionCreateRequest {

    @NotNull
    private Long postId;

    @NotBlank
    private String content;

    public QuestionCreateCommand toCommand() {
        return QuestionCreateCommand.builder()
            .postId(postId)
            .content(content)
            .build();
    }
}