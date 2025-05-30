package com.back2basics.adapter.persistence.post.adapter;

import static com.back2basics.adapter.persistence.comment.QCommentEntity.commentEntity;
import static com.back2basics.adapter.persistence.post.QPostEntity.postEntity;

import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostSearchPort;
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
    public Page<Post> searchByKeyword(String keyword, Pageable pageable) {

        // id 페이징
        List<Long> ids = queryFactory
            .select(postEntity.id)
            .from(postEntity)
            .where(
                postEntity.deletedAt.isNull()
                    .and(keyword == null || keyword.isBlank() ? null :
                        postEntity.title.contains(keyword)
                            .or(postEntity.content.contains(keyword)))
            )
            .orderBy(postEntity.createdAt.desc(), postEntity.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 조인
        List<Post> posts = queryFactory
            .selectFrom(postEntity)
            .distinct()
            .leftJoin(postEntity.comments, commentEntity).fetchJoin()
            .where(
                postEntity.deletedAt.isNull()
                    .and(postEntity.id.in(ids))
                    .and(keyword == null || keyword.isBlank() ? null :
                        postEntity.title.contains(keyword)
                            .or(postEntity.content.contains(keyword)))
            )
            .orderBy(postEntity.createdAt.desc(), postEntity.id.desc())
            .fetch()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        // 카운트 쿼리
        Long total = queryFactory
            .select(postEntity.count())
            .from(postEntity)
            .where(
                postEntity.deletedAt.isNull()
                    .and(keyword == null || keyword.isBlank() ? null :
                        postEntity.title.contains(keyword)
                            .or(postEntity.content.contains(keyword)))
            )
            .fetchOne();

        return new PageImpl<>(posts, pageable, total);

    }
}
