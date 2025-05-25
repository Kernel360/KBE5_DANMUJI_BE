package com.back2basics.comment.adapter.out;

import com.back2basics.comment.entity.CommentEntity;
import com.back2basics.comment.mapper.CommentMapper;
import com.back2basics.comment.repository.CommentEntityRepository;
import com.back2basics.model.comment.Comment;
import com.back2basics.port.out.comment.CommentRepositoryPort;
import com.back2basics.post.entity.PostEntity;
import com.back2basics.post.repository.PostEntityRepository;
import com.back2basics.service.comment.exception.CommentErrorCode;
import com.back2basics.service.comment.exception.CommentException;
import com.back2basics.service.post.exception.PostErrorCode;
import com.back2basics.service.post.exception.PostException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentJpaAdapter implements CommentRepositoryPort {

    private final CommentEntityRepository commentRepository;
    private final PostEntityRepository postEntityRepository;
    private final CommentMapper mapper;

    @Override
    public Long save(Comment comment) {
        CommentEntity entity = mapper.fromDomain(comment);

        PostEntity postEntity = findPostEntity(comment.getPostId());
        entity.assignPost(postEntity);

        if (comment.getParentCommentId() != null) {
            CommentEntity parentEntity = findParentEntity(comment.getParentCommentId());
            entity.assignParentComment(parentEntity);
            parentEntity.addChildComment(entity);
        }

        return commentRepository.save(entity).getId();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void update(Comment comment) {
        CommentEntity entity = mapper.fromDomain(comment);

        PostEntity postEntity = findPostEntity(comment.getPostId());
        entity.assignPost(postEntity);

        if (comment.getParentCommentId() != null) {
            CommentEntity parentEntity = findParentEntity(comment.getParentCommentId());
            entity.assignParentComment(parentEntity);
        }

        commentRepository.save(mapper.fromDomain(comment));
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(mapper.fromDomain(comment));
    }

    public PostEntity findPostEntity(Long postId) {
        return postEntityRepository.findById(postId)
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));
    }

    public CommentEntity findParentEntity(Long parentId) {
        return commentRepository.findById(parentId)
            .orElseThrow(
                () -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
    }
}
