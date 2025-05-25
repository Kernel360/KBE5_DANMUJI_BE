package com.back2basics.port.in.comment;

import com.back2basics.service.comment.dto.CommentCreateCommand;

public interface CreateCommentUseCase {

    Long createComment(CommentCreateCommand command);
}
