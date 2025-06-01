package com.back2basics.adapter.persistence.comment.adapter;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentUpdatePort;
import com.back2basics.service.comment.exception.CommentErrorCode;
import com.back2basics.service.comment.exception.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentUpdateJpaAdapter implements CommentUpdatePort {

    private final CommentEntityRepository commentRepository;
    private final CommentMapper mapper;

    @Override
    public void update(Comment comment) {
        // CommentEntity entity = mapper.toEntity(comment);

        CommentEntity entity = commentRepository.findById(comment.getId())
            .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        entity.updateContent(comment.getContent());

        commentRepository.save(entity);
    }
}
