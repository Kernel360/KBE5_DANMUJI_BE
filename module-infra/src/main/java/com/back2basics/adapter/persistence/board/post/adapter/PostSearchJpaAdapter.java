package com.back2basics.adapter.persistence.board.post.adapter;

import static com.back2basics.adapter.persistence.board.post.QPostEntity.postEntity;
import static com.back2basics.adapter.persistence.comment.QCommentEntity.commentEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.board.post.PostMapper;
import com.back2basics.adapter.persistence.board.post.dto.PostSummaryProjection;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.board.post.port.in.command.PostSearchCommand;
import com.back2basics.board.post.port.out.PostSearchPort;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
                userEntity.username.as("authorUsername"),
                userEntity.role.as("authorRole"),
                postEntity.title,
                postEntity.type,
                postEntity.priority,
                postEntity.createdAt,
                JPAExpressions
                    .select(commentEntity.count())
                    .from(commentEntity)
                    .where(
                        commentEntity.postId.eq(postEntity.id),
                        commentEntity.deletedAt.isNull()
                    )
            ))
            .from(postEntity)
            .join(userEntity).on(postEntity.authorId.eq(userEntity.id))
            .where(
                postEntity.projectId.eq(command.getProjectId()),
                //postEntity.projectStepId.eq(command.getProjectStepId()),
                activePosts(),
                matchesTitle(command.getTitle()),
                matchesAuthor(command.getAuthor()),
                matchesPriority(command.getPriority()),
                matchesType(command.getType()),
                matchesStep(command.getProjectStepId())
            )
            .orderBy(postEntity.id.desc())
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
                postEntity.projectId.eq(command.getProjectId()),
                //postEntity.projectStepId.eq(command.getProjectStepId()),
                activePosts(),
                matchesTitle(command.getTitle()),
                matchesAuthor(command.getAuthor()),
                matchesPriority(command.getPriority()),
                matchesType(command.getType()),
                matchesStep(command.getProjectStepId())
            );

        return PageableExecutionUtils.getPage(
            posts,
            pageable,
            () -> Optional.ofNullable(countQuery.fetchOne()).orElse(0L)
        );
    }

    @Override
    public Page<Post> searchWithCountQuery(PostSearchCommand command, Pageable pageable) {
        List<PostSummaryProjection> results = queryFactory
            .select(Projections.constructor(
                PostSummaryProjection.class,
                postEntity.id.as("postId"),
                postEntity.parentId,
                postEntity.projectId,
                postEntity.projectStepId,
                postEntity.authorId,
                userEntity.name.as("authorName"),
                userEntity.username.as("authorUsername"),
                userEntity.role.as("authorRole"),
                postEntity.title,
                postEntity.type,
                postEntity.priority,
                postEntity.createdAt,
                JPAExpressions
                    .select(commentEntity.count())
                    .from(commentEntity)
                    .where(
                        commentEntity.postId.eq(postEntity.id),
                        commentEntity.deletedAt.isNull()
                    )
            ))
            .from(postEntity)
            .join(userEntity).on(postEntity.authorId.eq(userEntity.id))
            .where(
                postEntity.projectId.eq(command.getProjectId()),
                activePosts(),
                matchesTitle(command.getTitle()),
                matchesAuthor(command.getAuthor()),
                matchesPriority(command.getPriority()),
                matchesType(command.getType()),
                matchesStep(command.getProjectStepId())
            )
            .orderBy(postEntity.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<Post> posts = results.stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        Long totalCount = queryFactory
            .select(postEntity.count())
            .from(postEntity)
            .where(
                postEntity.projectId.eq(command.getProjectId()),
                activePosts(),
                matchesTitle(command.getTitle()),
                matchesAuthor(command.getAuthor()),
                matchesPriority(command.getPriority()),
                matchesType(command.getType()),
                matchesStep(command.getProjectStepId())
            )
            .fetchOne();

        return new PageImpl<>(posts, pageable, Optional.ofNullable(totalCount).orElse(0L));
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

    private BooleanExpression matchesStep(Long stepId) {
        return (stepId == null) ? null : postEntity.projectStepId.eq(stepId);
    }
}