package com.back2basics.adapter.persistence.comment.utils;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.service.comment.exception.CommentErrorCode;
import com.back2basics.service.comment.exception.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentDeleteHelper {

    private final CommentEntityRepository commentRepository;

    @Transactional
    public void deleteCommentWithOrphanKeep(Long commentId) {
        CommentEntity parent = commentRepository.findById(commentId)
            .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        parent.getChildrenComments().forEach(child -> child.assignParentComment(null));
        parent.getChildrenComments().clear();

        commentRepository.delete(parent);
    }
}