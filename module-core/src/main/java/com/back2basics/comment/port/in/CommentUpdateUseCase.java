package com.back2basics.comment.port.in;

import com.back2basics.comment.port.in.command.CommentUpdateCommand;

public interface CommentUpdateUseCase {

    void updateComment(Long userId, Long commentId, CommentUpdateCommand command);

}
