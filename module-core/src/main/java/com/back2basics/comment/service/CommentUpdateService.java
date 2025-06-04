package com.back2basics.comment.service;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.CommentUpdateUseCase;
import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import com.back2basics.comment.port.out.CommentUpdatePort;
import com.back2basics.infra.validation.validator.CommentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentUpdateService implements CommentUpdateUseCase {

    private final CommentValidator commentValidator;
    private final CommentUpdatePort commentUpdatePort;


    @Override
    public void updateComment(Long userId, Long commentId, CommentUpdateCommand command) {
        Comment comment = commentValidator.findComment(commentId);
        commentValidator.isAuthor(comment, userId);

        comment.update(command);
        commentUpdatePort.update(comment);
    }
}
