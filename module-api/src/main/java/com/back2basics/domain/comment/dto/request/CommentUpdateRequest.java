package com.back2basics.domain.comment.dto.request;

import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public CommentUpdateCommand toCommand() {
        return CommentUpdateCommand.builder()
            .content(content)
            .build();
    }
}
