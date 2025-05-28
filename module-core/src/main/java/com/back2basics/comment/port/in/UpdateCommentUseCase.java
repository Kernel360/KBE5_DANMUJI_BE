package com.back2basics.comment.port.in;

import com.back2basics.service.comment.dto.CommentUpdateCommand;

public interface UpdateCommentUseCase {

    void updateComment(Long id, CommentUpdateCommand command);

}
