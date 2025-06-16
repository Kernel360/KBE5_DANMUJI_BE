package com.back2basics.adapter.persistence.comment.adapter;

import static com.back2basics.adapter.persistence.comment.QCommentEntity.commentEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentReadPort;
import com.back2basics.comment.service.result.CommentWithPostAndAuthorResult;
import com.querydsl.core.types.Projections;
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
        CommentWithPostAndAuthorResult result = queryFactory
            .select(Projections.constructor(
                CommentWithPostAndAuthorResult.class,
                commentEntity.id,
                commentEntity.parentId,
                commentEntity.postId,
                commentEntity.authorId,
                commentEntity.authorIp,
                userEntity.name,
                commentEntity.content,
                commentEntity.createdAt,
                commentEntity.updatedAt
            ))
            .from(commentEntity)
            .join(userEntity).on(commentEntity.authorId.eq(userEntity.id))
            .where(
                commentEntity.id.eq(id),
                commentEntity.deletedAt.isNull()
            )
            .fetchOne();

        return Optional.ofNullable(result).map(mapper::toDomain);
    }

    @Override
    public List<Comment> findAllCommentsByPostId(Long postId) {
        List<CommentWithPostAndAuthorResult> results = queryFactory
            .select(Projections.constructor(
                CommentWithPostAndAuthorResult.class,
                commentEntity.id,
                commentEntity.parentId,
                commentEntity.postId,
                commentEntity.authorId,
                commentEntity.authorIp,
                userEntity.name,
                commentEntity.content,
                commentEntity.createdAt,
                commentEntity.updatedAt
            ))
            .from(commentEntity)
            .join(userEntity).on(commentEntity.authorId.eq(userEntity.id))
            .where(
                commentEntity.postId.eq(postId),
                commentEntity.deletedAt.isNull()
            )
            .fetch();

        return results.stream()
            .map(mapper::toDomain)
            .toList();
    }
}
