package com.back2basics.comment.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUpdateCommand {

    // todo : 식별값 id로 변경
    private Long requesterId;
    private String content;

}