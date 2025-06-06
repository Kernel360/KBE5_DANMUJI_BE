package com.back2basics.comment.port.in;

import com.back2basics.comment.port.in.command.CommentCreateCommand;

public interface CommentCreateUseCase {

    Long createComment(Long userId, String userIp, CommentCreateCommand command);
}
