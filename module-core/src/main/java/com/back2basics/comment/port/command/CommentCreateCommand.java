package com.back2basics.comment.port.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateCommand {

    private Long postId;
    private Long parentId; // 이게 null이 아니면 대댓글임
    private Long authorId;
    private String content;
}
