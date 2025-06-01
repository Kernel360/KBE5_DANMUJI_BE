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
        // 페이징 조회
        List<Post> posts = queryFactory
            .selectFrom(postEntity)
            .where(postEntity.deletedAt.isNull())
            .orderBy(postEntity.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        // 카운트 쿼리
        Long total = queryFactory
            .select(postEntity.count())
            .from(postEntity)
            .where(postEntity.deletedAt.isNull())
            .fetchOne();

        return new PageImpl<>(posts, pageable, total);
    }

}
