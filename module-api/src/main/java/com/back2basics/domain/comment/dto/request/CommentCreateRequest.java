package com.back2basics.domain.comment.dto.request;

import com.back2basics.comment.port.command.CommentCreateCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotNull(message = "게시글 id를 입력해주세요.")
    private Long postId;

    @Nullable
    private Long parentId; // 이게 null이 아니면 대댓글임
    
    private Long authorId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public CommentCreateCommand toCommand() {
        return CommentCreateCommand.builder()
            .postId(postId)
            .parentId(parentId)
            .authorId(authorId)
            .content(content)
            .build();
    }

}
