package com.back2basics.adapter.persistence.post.adapter;

import static com.back2basics.adapter.persistence.post.QPostEntity.postEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
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
    public Page<Post> search(String title, String clientCompany, String developerCompany,
        String author, Integer priority, PostStatus status, PostType type,
        Pageable pageable) {

        // id 페이징
        List<Long> ids = queryFactory
            .select(postEntity.id)
            .from(postEntity)
            .where(activePosts()
                .and(matchesTitle(title))
                //.and(matchesClientCompany(searchCommand.getClientCompany()))
                //.and(matchesDeveloperCompany(searchCommand.getDeveloperCompany()))
                .and(matchesAuthor(author))
                .and(matchesPriority(priority))
                .and(matchesStatus(status))
                .and(matchesType(type)))
            .orderBy(postEntity.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // post 조회
        List<Post> posts = queryFactory
            .selectFrom(postEntity)
            .join(postEntity.author, userEntity).fetchJoin()
            .where(postEntity.id.in(ids)
                .and(activePosts())
                .and(matchesTitle(title))
                //.and(matchesClientCompany(searchCommand.getClientCompany()))
                //.and(matchesDeveloperCompany(searchCommand.getDeveloperCompany()))
                .and(matchesAuthor(author))
                .and(matchesPriority(priority))
                .and(matchesStatus(status))
                .and(matchesType(type)))
            .orderBy(postEntity.createdAt.desc())
            .fetch()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        // 카운트 쿼리
        Long total = queryFactory
            .select(postEntity.count())
            .from(postEntity)
            .where(activePosts()
                .and(matchesTitle(title))
                //.and(matchesClientCompany(searchCommand.getClientCompany()))
                //.and(matchesDeveloperCompany(searchCommand.getDeveloperCompany()))
                .and(matchesAuthor(author))
                .and(matchesPriority(priority))
                .and(matchesStatus(status))
                .and(matchesType(type)))
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

    // todo : ProjectEntity에 해당 필드가 추가되면 필터링 옵션에 넣을 예정
//    private BooleanExpression matchesClientCompany(String clientCompany) {
//        return (clientCompany == null || clientCompany.isBlank()) ? null
//            : postEntity.project.clientCompany.contains(clientCompany);
//    }
//
//    private BooleanExpression matchesDeveloperCompany(String developerCompany) {
//        return (developerCompany == null || developerCompany.isBlank()) ? null
//            : postEntity.project.developerCompany.contains(developerCompany);
//    }

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
