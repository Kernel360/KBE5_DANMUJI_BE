package com.back2basics.domain.question.dto.request;


import com.back2basics.question.port.in.command.QuestionDeleteCommand;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class QuestionDeleteRequest {

    @NotNull(message = "요청자 ID는 필수입니다.")
    private Long requesterId;

    public QuestionDeleteCommand toCommand() {
        return QuestionDeleteCommand.builder()
            .requesterId(requesterId)
            .build();
    }
}
