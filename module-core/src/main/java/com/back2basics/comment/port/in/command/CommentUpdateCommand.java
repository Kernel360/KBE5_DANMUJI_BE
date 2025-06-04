package com.back2basics.comment.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUpdateCommand {
    
    private String content;

}