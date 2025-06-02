package com.back2basics.adapter.persistence.comment.adapter;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentDeletePort;
import com.back2basics.infra.exception.comment.CommentErrorCode;
import com.back2basics.infra.exception.comment.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDeleteJpaAdapter implements CommentDeletePort {

    private final CommentEntityRepository commentRepository;

    @Override
    public void delete(Comment comment) {
        CommentEntity entity = commentRepository.findById(comment.getId())
            .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        // 자식 댓글들의 부모 참조 제거
        entity.getChildrenComments().forEach(child ->
            child.assignParentComment(null));

        commentRepository.delete(entity);
    }
}
