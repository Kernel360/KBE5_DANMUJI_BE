package com.back2basics.comment.utils;

import com.back2basics.comment.entity.CommentEntity;
import com.back2basics.comment.repository.CommentEntityRepository;
import com.back2basics.model.comment.Comment;
import com.back2basics.service.comment.exception.CommentErrorCode;
import com.back2basics.service.comment.exception.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentUpdateHelper {

    private final CommentEntityRepository commentRepository;

    @Transactional
    public CommentEntity updateComment(Long commentId, Comment comment) {
        CommentEntity entity = commentRepository.findById(commentId)
            .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        entity.updateContent(comment.getContent());
        return entity;
    }
}
