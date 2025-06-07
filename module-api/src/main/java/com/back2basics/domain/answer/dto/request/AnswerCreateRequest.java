package com.back2basics.domain.answer.dto.request;

import com.back2basics.answer.port.in.command.AnswerCreateCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AnswerCreateRequest {

    @NotNull(message = "질문 id를 입력해주세요.")
    private Long questionId;

    @Nullable
    private Long parentId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public AnswerCreateCommand toCommand() {
        return AnswerCreateCommand.builder()
            .questionId(questionId)
            .parentId(parentId)
            .content(content)
            .build();
    }

}
