package com.back2basics.domain.comment.dto.request;

import com.back2basics.comment.port.command.CommentUpdateCommand;
import jakarta.validation.constraints.NotBlank;

public class CommentUpdateRequest {

    // todo : 식별값 id로 변경 예정
    private String requesterName;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public CommentUpdateCommand toCommand() {
        return CommentUpdateCommand.builder()
            .requesterName(requesterName)
            .content(content)
            .build();
    }
}
