package com.back2basics.infra.validation.validator;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentReadPort;
import com.back2basics.service.comment.exception.CommentErrorCode;
import com.back2basics.service.comment.exception.CommentException;
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

    public void isAuthor(Comment comment, String requesterName) {
        if (!comment.getAuthorName().equals(requesterName)) {
            throw new CommentException(CommentErrorCode.INVALID_COMMENT_AUTHOR);
        }
    }
}
