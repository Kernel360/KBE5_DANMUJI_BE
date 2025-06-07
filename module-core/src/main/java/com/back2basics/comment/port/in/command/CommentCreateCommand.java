package com.back2basics.comment.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateCommand {

    private Long postId;
    private Long parentId;
    private String content;
}
