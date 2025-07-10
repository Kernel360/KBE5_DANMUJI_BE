package com.back2basics.domain.comment.dto.request;

import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import com.back2basics.custom.CustomStringLengthCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    @CustomStringLengthCheck(min = 1, max = 50, message = "댓글 내용은 50자 이하로 입력해주세요")
    private String content;

    public CommentUpdateCommand toCommand() {
        return CommentUpdateCommand.builder()
            .content(content)
            .build();
    }
}
