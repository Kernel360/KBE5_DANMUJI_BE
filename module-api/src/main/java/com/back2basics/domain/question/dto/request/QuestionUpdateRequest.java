package com.back2basics.domain.question.dto.request;


import com.back2basics.question.port.in.command.QuestionUpdateCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class QuestionUpdateRequest {

    @NotNull
    private Long requesterId;

    @NotBlank
    private String content;

    public QuestionUpdateCommand toCommand() {
        return QuestionUpdateCommand.builder()
            .requesterId(requesterId)
            .content(content)
            .build();
    }
}
