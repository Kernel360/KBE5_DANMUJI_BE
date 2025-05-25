package com.back2basics.service.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateCommand {

    @NotNull
    private Long postId;

    private Long parentId; // 이게 null이 아니면 대댓글임

    private String authorName;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public CommentCreateCommand(Long postId, Long parentId, String authorName, String content) {
        this.postId = postId;
        this.parentId = parentId;
        this.authorName = authorName;
        this.content = content;
    }
}
