package com.back2basics.infra.comment.validation;

import com.back2basics.model.comment.Comment;
import com.back2basics.port.out.comment.CommentRepositoryPort;
import com.back2basics.service.comment.exception.CommentErrorCode;
import com.back2basics.service.comment.exception.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidator {

    private final CommentRepositoryPort commentRepository;

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
