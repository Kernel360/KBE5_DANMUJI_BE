package com.back2basics.adapter.persistence.post.adapter;

import static com.back2basics.adapter.persistence.post.QPostEntity.postEntity;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.comment.model.Comment;
import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
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
    private final CommentEntityRepository commentRepository;
    private final JPAQueryFactory queryFactory;
    private final PostMapper mapper;
    private final CommentMapper commentMapper;

    @Override
    public Optional<Post> findById(Long id) {
        PostEntity entity = postRepository.findById(id)
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        List<CommentEntity> commentEntities = commentRepository.findByPostId(entity.getId());
        List<Comment> commentTree = commentMapper.toDomainHierarchy(commentEntities);
        return Optional.of(mapper.toDomain(entity, commentTree));
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
