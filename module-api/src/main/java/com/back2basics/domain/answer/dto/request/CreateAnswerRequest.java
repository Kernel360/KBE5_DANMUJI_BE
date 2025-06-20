package com.back2basics.domain.answer.dto.request;

import com.back2basics.answer.port.in.command.CreateAnswerCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateAnswerRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public CreateAnswerCommand toCommand() {
        return CreateAnswerCommand.builder()
            .content(content)
            .build();
    }

}
