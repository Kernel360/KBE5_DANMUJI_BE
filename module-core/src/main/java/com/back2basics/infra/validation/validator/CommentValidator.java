package com.back2basics.infra.validation.validator;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentReadPort;
import com.back2basics.infra.exception.comment.CommentErrorCode;
import com.back2basics.infra.exception.comment.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidator {

    private final CommentReadPort commentRepository;

    public Comment findComment(Long id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
    }

    public void isAuthor(Comment comment, Long requesterId) {
        if (!comment.getAuthorId().equals(requesterId)) {
            throw new CommentException(CommentErrorCode.INVALID_COMMENT_AUTHOR);
        }
    }
}
