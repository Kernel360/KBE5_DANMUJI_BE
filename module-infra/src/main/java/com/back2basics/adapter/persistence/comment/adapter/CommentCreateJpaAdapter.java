package com.back2basics.adapter.persistence.comment.adapter;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreateJpaAdapter implements CommentCreatePort {

    private final CommentEntityRepository commentRepository;
    private final CommentRelationHelper commentRelationHelper;
    private final CommentMapper mapper;

    @Override
    public Long save(Comment comment) {
        CommentEntity entity = mapper.fromDomain(comment);
        commentRelationHelper.assignRelations(entity, comment.getPostId(),
            comment.getParentCommentId());
        return commentRepository.save(entity).getId();
    }

}
