package com.back2basics.comment.service;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.CommentDeleteUseCase;
import com.back2basics.comment.port.out.CommentDeletePort;
import com.back2basics.infra.validation.validator.CommentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentDeleteService implements CommentDeleteUseCase {

    private final CommentValidator commentValidator;
    private final CommentDeletePort commentDeletePort;

    @Override
    public void deleteComment(Long id, Long requesterId) {
        Comment comment = commentValidator.findComment(id);
        commentValidator.isAuthor(comment, requesterId);

        commentDeletePort.delete(comment);
    }
}
