package com.back2basics.domain.comment.dto.request;

import com.back2basics.comment.port.command.CommentUpdateCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    // todo : 식별값 id로 변경 예정
    private Long requesterId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public CommentUpdateCommand toCommand() {
        return CommentUpdateCommand.builder()
            .requesterId(requesterId)
            .content(content)
            .build();
    }
}
