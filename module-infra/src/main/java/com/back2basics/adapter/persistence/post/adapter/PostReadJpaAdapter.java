package com.back2basics.adapter.persistence.post.adapter;

import static com.back2basics.adapter.persistence.post.QPostEntity.postEntity;

import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostReadPort;
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

    private final PostEntityRepository postRepository;
    private final JPAQueryFactory queryFactory;
    private final PostMapper mapper;

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id).map(mapper::toDomain);
    }


    @Override
    public Page<Post> findAllWithPaging(Pageable pageable) {
        // id만 페이징
        List<Long> ids = queryFactory.select(postEntity.id)
            .from(postEntity)
            .where(postEntity.deletedAt.isNull())
            .orderBy(postEntity.createdAt.desc(), postEntity.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // fetch join
        List<Post> posts = queryFactory.selectFrom(postEntity)
            .distinct()
            .leftJoin(postEntity.comments).fetchJoin()
            .where(postEntity.id.in(ids), postEntity.deletedAt.isNull())
            .orderBy(postEntity.createdAt.desc(), postEntity.id.desc())
            .fetch()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        // 카운트 쿼리
        Long total = queryFactory.select(postEntity.count())
            .from(postEntity)
            .where(postEntity.deletedAt.isNull())
            .fetchOne();

        return new PageImpl<>(posts, pageable, total);
    }
//    쿼리 3개 나눈거랑 쿼리 로그 비교할 수 있게 만들어둔 메소드
//    @ToMany 관계 때문에 fetch join을 쓰고 limit + offset 로 페이징 시도 하는 경우
//    public Page<Post> findAllWithPaging(Pageable pageable) {
//        List<Post> posts = queryFactory
//            .selectFrom(postEntity)
//            .distinct()
//            .leftJoin(postEntity.comments).fetchJoin()
//            .where(postEntity.deletedAt.isNull())
//            .orderBy(postEntity.createdAt.desc(), postEntity.id.desc())
//            .offset(pageable.getOffset())
//            .limit(pageable.getPageSize())
//            .fetch()
//            .stream()
//            .map(mapper::toDomain)
//            .collect(Collectors.toList());
//
//        return new PageImpl<>(posts, pageable, posts.size());
//    }
}
