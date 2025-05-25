package com.back2basics.infra.comment.validation;

import com.back2basics.model.comment.Comment;
import com.back2basics.model.post.Post;
import com.back2basics.port.out.comment.CommentRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class CommentValidator {

    private final CommentRepositoryPort commentRepository;

    public Post findComment(Long id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
    }

    public void isAuthor(Comment comment, String requesterName) {
        if (!comment.getAuthorName().equals(requesterName)) {
            throw new CommentException(CommentErrorCode.INVALID_COMMENT_AUTHOR);
        }
    }
}
