package com.back2basics.adapter.persistence.comment.adapter;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentCreatePort;
import com.back2basics.service.comment.exception.CommentErrorCode;
import com.back2basics.service.comment.exception.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreateJpaAdapter implements CommentCreatePort {

    private final CommentEntityRepository commentRepository;
    private final CommentMapper mapper;

    @Override
    public Long save(Comment comment) {
        CommentEntity entity = mapper.toEntity(comment);

        if (comment.getParentCommentId() != null) {
            CommentEntity parent = commentRepository.findById(comment.getParentCommentId())
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
            entity.assignParentComment(parent);
        }

        return commentRepository.save(entity).getId();
    }

}
