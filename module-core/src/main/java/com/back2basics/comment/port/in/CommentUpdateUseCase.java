package com.back2basics.comment.port.in;

import com.back2basics.comment.port.command.CommentUpdateCommand;

public interface CommentUpdateUseCase {

    void updateComment(Long id, CommentUpdateCommand command);

}
