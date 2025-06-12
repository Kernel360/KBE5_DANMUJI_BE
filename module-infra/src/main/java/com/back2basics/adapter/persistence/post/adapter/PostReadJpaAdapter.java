package com.back2basics.adapter.persistence.post.adapter;

import static com.back2basics.adapter.persistence.post.QPostEntity.postEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.service.result.ReadRecentPostResult;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostReadJpaAdapter implements PostReadPort {

    private final JPAQueryFactory queryFactory;
    private final PostMapper mapper;

    @Override
    public Optional<Post> findById(Long id) {
        PostEntity entity = queryFactory
            .selectFrom(postEntity)
            .join(postEntity.author, userEntity).fetchJoin()
            .where(
                postEntity.id.eq(id),
                postEntity.deletedAt.isNull()
            )

            .fetchOne();

        if (entity == null) {
            throw new PostException(PostErrorCode.POST_NOT_FOUND);
        }

        return Optional.of(mapper.toDomain(entity));
    }

    @Override
    public Optional<Post> findPostByProjectStepId(Long postId, Long projectStepId) {
        PostEntity entity = queryFactory
            .selectFrom(postEntity)
            .join(postEntity.author, userEntity).fetchJoin()
            .where(
                postEntity.id.eq(postId),
                postEntity.deletedAt.isNull(),
                postEntity.projectStepId.eq(projectStepId)
            )

            .fetchOne();

        if (entity == null) {
            throw new PostException(PostErrorCode.POST_NOT_FOUND);
        }

        return Optional.of(mapper.toDomain(entity));
    }

    @Override
    public Page<Post> findAllPostsByProjectStepId(Long projectStepId, Pageable pageable) {
        // id 페이징
        List<Long> ids = queryFactory
            .select(postEntity.id)
            .from(postEntity)
            .where(
                postEntity.deletedAt.isNull(),
                postEntity.projectStepId.eq(projectStepId)
            )
            .orderBy(postEntity.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // id로 fetch join
        List<Post> posts = queryFactory
            .selectFrom(postEntity)
            .join(postEntity.author, userEntity).fetchJoin()
            .where(
                postEntity.id.in(ids),
                postEntity.deletedAt.isNull(),
                postEntity.projectStepId.eq(projectStepId)
            )
            .orderBy(postEntity.createdAt.desc())
            .fetch()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        // 카운트 쿼리
        Long total = queryFactory
            .select(postEntity.count())
            .from(postEntity)
            .where(
                postEntity.deletedAt.isNull(),
                postEntity.projectStepId.eq(projectStepId)
            )
            .fetchOne();

        // Unboxing of 'total' may produce 'NullPointerException'
        // total이 null일 수 있으며 그럴 경우 카운트쿼리가 결과를 반환하지 못해서
        // NPE 날 수 있다는 경고에 의한 조건 추가
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public List<ReadRecentPostResult> getRecentPosts() {
        return queryFactory
            .select(Projections.constructor(
                ReadRecentPostResult.class,
                postEntity.id,
                postEntity.title,
                postEntity.createdAt
            ))
            .from(postEntity)
            .where(postEntity.deletedAt.isNull())
            .orderBy(postEntity.createdAt.desc())
            .limit(5)
            .fetch();
    }

}
