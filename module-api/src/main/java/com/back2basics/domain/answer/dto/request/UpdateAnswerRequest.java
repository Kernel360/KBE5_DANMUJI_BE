package com.back2basics.domain.answer.dto.request;

import com.back2basics.answer.port.in.command.UpdateAnswerCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateAnswerRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public UpdateAnswerCommand toCommand() {
        return UpdateAnswerCommand.builder()
            .content(content)
            .build();
    }
}
