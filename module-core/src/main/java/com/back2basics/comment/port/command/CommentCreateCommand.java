package com.back2basics.comment.port.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateCommand {

    @NotNull
    private Long postId;

    private Long parentId; // 이게 null이 아니면 대댓글임

    private String authorName;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
