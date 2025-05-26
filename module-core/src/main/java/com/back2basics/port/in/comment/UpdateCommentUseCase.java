package com.back2basics.port.in.comment;

import com.back2basics.service.comment.dto.CommentUpdateCommand;

public interface UpdateCommentUseCase {

    void updateComment(Long id, CommentUpdateCommand command);

}
