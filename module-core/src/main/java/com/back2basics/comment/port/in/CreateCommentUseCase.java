package com.back2basics.comment.port.in;

import com.back2basics.comment.port.command.CommentCreateCommand;

public interface CreateCommentUseCase {

    Long createComment(CommentCreateCommand command);
}
