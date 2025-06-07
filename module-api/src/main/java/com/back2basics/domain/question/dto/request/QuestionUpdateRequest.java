package com.back2basics.domain.question.dto.request;


import com.back2basics.question.port.in.command.QuestionUpdateCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class QuestionUpdateRequest {

    @NotBlank
    private String content;

    public QuestionUpdateCommand toCommand() {
        return QuestionUpdateCommand.builder()
            .content(content)
            .build();
    }
}
