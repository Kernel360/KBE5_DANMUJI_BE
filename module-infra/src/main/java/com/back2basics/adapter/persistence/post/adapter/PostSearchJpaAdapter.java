package com.back2basics.adapter.persistence.post.adapter;

import static com.back2basics.adapter.persistence.post.QPostEntity.postEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.in.command.PostSearchCommand;
import com.back2basics.post.port.out.PostSearchPort;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostSearchJpaAdapter implements PostSearchPort {

    private final JPAQueryFactory queryFactory;
    private final PostMapper mapper;

    @Override
    public Page<Post> search(PostSearchCommand command, Pageable pageable) {

        // id 페이징
        List<Long> ids = queryFactory
            .select(postEntity.id)
            .from(postEntity)
            .where(
                postEntity.projectStepId.eq(command.getProjectStepId())
                    .and(activePosts())
                    .and(matchesTitle(command.getTitle()))
                    .and(matchesAuthor(command.getAuthor()))
                    .and(matchesPriority(command.getPriority()))
                    .and(matchesStatus(command.getStatus()))
                    .and(matchesType(command.getType())))
            .orderBy(postEntity.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // post 조회
        List<Post> posts = queryFactory
            .selectFrom(postEntity)
            .join(postEntity.author, userEntity).fetchJoin()
            .where(postEntity.id.in(ids)
                .and(postEntity.projectStepId.eq(command.getProjectStepId()))
                .and(activePosts())
                .and(matchesTitle(command.getTitle()))
                .and(matchesAuthor(command.getAuthor()))
                .and(matchesPriority(command.getPriority()))
                .and(matchesStatus(command.getStatus()))
                .and(matchesType(command.getType())))
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
                postEntity.projectStepId.eq(command.getProjectStepId())
                    .and(activePosts())
                    .and(matchesTitle(command.getTitle()))
                    .and(matchesAuthor(command.getAuthor()))
                    .and(matchesPriority(command.getPriority()))
                    .and(matchesStatus(command.getStatus()))
                    .and(matchesType(command.getType())))
            .fetchOne();

        return new PageImpl<>(posts, pageable, total);

    }

    private BooleanExpression activePosts() {
        return postEntity.deletedAt.isNull();
    }

    private BooleanExpression matchesTitle(String title) {
        return (title == null || title.isBlank()) ? null : postEntity.title.contains(title);
    }

    private BooleanExpression matchesAuthor(String author) {
        return (author == null || author.isBlank()) ? null
            : postEntity.author.name.contains(author);
    }

    private BooleanExpression matchesPriority(Integer priority) {
        return (priority == null) ? null : postEntity.priority.eq(priority);
    }

    private BooleanExpression matchesStatus(PostStatus status) {
        return (status == null) ? null : postEntity.status.eq(status);
    }

    private BooleanExpression matchesType(PostType type) {
        return (type == null) ? null : postEntity.type.eq(type);
    }
}
