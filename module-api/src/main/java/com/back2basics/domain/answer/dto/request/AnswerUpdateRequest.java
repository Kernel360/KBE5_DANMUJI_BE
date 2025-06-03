package com.back2basics.domain.answer.dto.request;

import com.back2basics.answer.port.in.command.AnswerUpdateCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AnswerUpdateRequest {

    private Long requesterId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public AnswerUpdateCommand toCommand() {
        return AnswerUpdateCommand.builder()
            .requesterId(requesterId)
            .content(content)
            .build();
    }
}
