package com.back2basics.comment.port.in;

import com.back2basics.service.comment.dto.CommentCreateCommand;

public interface CreateCommentUseCase {

    Long createComment(CommentCreateCommand command);
}
