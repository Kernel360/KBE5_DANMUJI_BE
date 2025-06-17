package com.back2basics.adapter.persistence.post.adapter;

import static com.back2basics.adapter.persistence.post.QPostEntity.postEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.adapter.persistence.post.adapter.projection.PostSummaryProjection;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.in.command.PostSearchCommand;
import com.back2basics.post.port.out.PostSearchPort;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
public class PostSearchJpaAdapter implements PostSearchPort {

    private final JPAQueryFactory queryFactory;
    private final PostMapper mapper;

    @Override
    public Page<Post> search(PostSearchCommand command, Pageable pageable) {
        List<PostSummaryProjection> results = queryFactory
            .select(Projections.constructor(
                PostSummaryProjection.class,
                postEntity.id.as("postId"),
                postEntity.parentId,
                postEntity.projectId,
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
                postEntity.projectStepId.eq(command.getProjectStepId()),
                activePosts(),
                matchesTitle(command.getTitle()),
                matchesAuthor(command.getAuthor()),
                matchesPriority(command.getPriority()),
                matchesType(command.getType())
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
                postEntity.projectStepId.eq(command.getProjectStepId()),
                activePosts(),
                matchesTitle(command.getTitle()),
                matchesAuthor(command.getAuthor()),
                matchesPriority(command.getPriority()),
                matchesType(command.getType())
            );

        return PageableExecutionUtils.getPage(
            posts,
            pageable,
            () -> Optional.ofNullable(countQuery.fetchOne()).orElse(0L)
        );
    }

    private BooleanExpression activePosts() {
        return postEntity.deletedAt.isNull();
    }

    private BooleanExpression matchesTitle(String title) {
        return (title == null || title.isBlank()) ? null : postEntity.title.contains(title);
    }

    private BooleanExpression matchesAuthor(String author) {
        return (author == null || author.isBlank()) ? null : userEntity.name.contains(author);
    }

    private BooleanExpression matchesPriority(PostPriority priority) {
        return (priority == null) ? null : postEntity.priority.eq(priority);
    }

    private BooleanExpression matchesType(PostType type) {
        return (type == null) ? null : postEntity.type.eq(type);
    }
}