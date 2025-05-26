package com.back2basics.comment.adapter.out;

import com.back2basics.comment.entity.CommentEntity;
import com.back2basics.comment.mapper.CommentMapper;
import com.back2basics.comment.repository.CommentEntityRepository;
import com.back2basics.model.comment.Comment;
import com.back2basics.port.out.comment.CommentRepositoryPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentJpaAdapter implements CommentRepositoryPort {

    private final CommentEntityRepository commentRepository;
    private final CommentRelationAdapter commentRelationAdapter;
    private final CommentMapper mapper;

    @Override
    public Long save(Comment comment) {
        CommentEntity entity = mapper.fromDomain(comment);
        commentRelationAdapter.assignRelations(entity, comment.getPostId(),
            comment.getParentCommentId());

        return commentRepository.save(entity).getId();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void update(Comment comment) {
        CommentEntity entity = mapper.fromDomain(comment);
        commentRelationAdapter.assignRelations(entity, comment.getPostId(),
            comment.getParentCommentId());

        commentRepository.save(mapper.fromDomain(comment));
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(mapper.fromDomain(comment));
    }

}
