package com.back2basics.comment.adapter.out;

import com.back2basics.comment.entity.CommentEntity;
import com.back2basics.comment.mapper.CommentMapper;
import com.back2basics.comment.repository.CommentEntityRepository;
import com.back2basics.comment.utils.CommentDeleteHelper;
import com.back2basics.comment.utils.CommentRelationHelper;
import com.back2basics.comment.utils.CommentUpdateHelper;
import com.back2basics.model.comment.Comment;
import com.back2basics.port.out.comment.CommentRepositoryPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentJpaAdapter implements CommentRepositoryPort {

    private final CommentEntityRepository commentRepository;
    private final CommentRelationHelper commentRelationHelper;
    private final CommentDeleteHelper commentDeleteHelper;
    private final CommentUpdateHelper commentUpdateHelper;
    private final CommentMapper mapper;

    @Override
    public Long save(Comment comment) {
        CommentEntity entity = mapper.fromDomain(comment);
        commentRelationHelper.assignRelations(entity, comment.getPostId(),
            comment.getParentCommentId());
        return commentRepository.save(entity).getId();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void update(Comment comment) {
        // create 과 다르게 기존에 있는 댓글을 사용해야 한다는 점에서 jpa.findById() 가 필요
        CommentEntity entity = commentUpdateHelper.updateComment(comment.getId(), comment);
        commentRelationHelper.assignRelations(entity, comment.getPostId(),
            comment.getParentCommentId());

        commentRepository.save(entity);
    }

    @Override
    public void delete(Comment comment) {
        CommentEntity entity = mapper.fromDomain(comment);
        commentDeleteHelper.deleteCommentWithOrphanKeep(entity.getId());

        commentRepository.delete(entity);
    }

}
