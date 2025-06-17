package com.back2basics.adapter.persistence.post.adapter;

import static com.back2basics.adapter.persistence.post.QPostEntity.postEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;
import static com.back2basics.infra.exception.post.PostErrorCode.POST_NOT_FOUND;

import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.adapter.persistence.post.adapter.projection.PostDetailResult;
import com.back2basics.adapter.persistence.post.adapter.projection.PostSummaryResult;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.service.result.ReadRecentPostResult;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostReadJpaAdapter implements PostReadPort {

    private final JPAQueryFactory queryFactory;
    private final PostMapper mapper;

    @Override
    public Optional<Post> findById(Long id) {
        PostDetailResult result = queryFactory
            .select(Projections.constructor(
                PostDetailResult.class,
                postEntity.id.as("postId"),
                postEntity.parentId,
                postEntity.projectId,
                postEntity.projectStepId,
                postEntity.authorIp,
                postEntity.authorId,
                userEntity.name.as("authorName"),
                postEntity.title,
                postEntity.content,
                postEntity.type,
                postEntity.priority,
                postEntity.createdAt,
                postEntity.updatedAt
            ))
            .from(postEntity)
            .join(userEntity).on(postEntity.authorId.eq(userEntity.id))
            .where(
                postEntity.id.eq(id),
                postEntity.deletedAt.isNull()
            )
            .fetchOne();

        // softdelete 처리 된 게시글을 조회했을 경우에는 where절에 의해 result가 null이 나오고
        // 그래서 NPE 발생...
        if (result == null) {
            throw new PostException(POST_NOT_FOUND);
        }

        return Optional.of(mapper.toDomain(result));
    }

    @Override
    public Page<Post> findAllPostsByProjectIdAndStepId(Long projectId, Long projectStepId,
        Pageable pageable) {

        List<PostSummaryResult> results = queryFactory
            .select(Projections.constructor(
                PostSummaryResult.class,
                postEntity.id.as("postId"),
                postEntity.projectId,
                postEntity.parentId,
                postEntity.projectStepId,
                postEntity.authorId,
                userEntity.name.as("authorName"),
                postEntity.title,
                postEntity.type,
                postEntity.priority,
                postEntity.createdAt
            ))
            .from(postEntity)
            .join(userEntity).on(postEntity.authorId.eq(userEntity.id))
            .where(
                postEntity.deletedAt.isNull(),
                postEntity.projectId.eq(projectId),
                postEntity.projectStepId.eq(projectStepId)
            )
            .orderBy(postEntity.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<Post> posts = results.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        JPAQuery<Long> countQuery = queryFactory
            .select(postEntity.count())
            .from(postEntity)
            .where(
                postEntity.deletedAt.isNull(),
                postEntity.projectId.eq(projectId),
                postEntity.projectStepId.eq(projectStepId)
            );

        return PageableExecutionUtils.getPage(
            posts,
            pageable,
            () -> Optional.ofNullable(countQuery.fetchOne()).orElse(0L)
        );
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
