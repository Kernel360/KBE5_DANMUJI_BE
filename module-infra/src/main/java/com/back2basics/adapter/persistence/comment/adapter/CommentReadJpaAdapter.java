package com.back2basics.adapter.persistence.comment.adapter;

import static com.back2basics.adapter.persistence.comment.QCommentEntity.commentEntity;
import static com.back2basics.adapter.persistence.post.QPostEntity.postEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentReadPort;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReadJpaAdapter implements CommentReadPort {

    private final CommentMapper mapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Comment> findById(Long id) {
        CommentEntity entity = queryFactory
            .selectFrom(commentEntity)
            .join(commentEntity.author, userEntity).fetchJoin()
            .join(commentEntity.post, postEntity).fetchJoin()
            .where(
                commentEntity.id.eq(id),
                commentEntity.deletedAt.isNull()
            )
            .fetchOne();

        return Optional.ofNullable(entity).map(mapper::toDomain);
    }

    @Override
    public List<Comment> findAllCommentsByPostId(Long postId) {
        List<CommentEntity> commentEntities = queryFactory
            .selectFrom(commentEntity)
            .join(commentEntity.author, userEntity).fetchJoin()
            .join(commentEntity.post, postEntity).fetchJoin()
            .where(
                commentEntity.post.id.eq(postId),
                commentEntity.deletedAt.isNull()
            )
            .fetch();

        return commentEntities.stream()
            .map(mapper::toDomain)
            .toList();
    }
}
