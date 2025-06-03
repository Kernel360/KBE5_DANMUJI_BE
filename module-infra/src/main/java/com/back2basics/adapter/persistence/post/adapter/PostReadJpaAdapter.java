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

        // todo : N+1 관련 문제 확인해봐야함
        // 게시글에서 ToMany쪽인 댓글을 조회하는 것이고, 단방향이기 때문에 게시글에는 댓글 정보가 없음
        // 따라서 쿼리를 통해 조인을 걸어줘서 연관 엔티티를 가져오도록 했음
        // 또한 페이징은 게시글에만 걸려있는 상항임
        // 만약 댓글에도 페이징을 걸어줬다면 fetch join을 걸어주는 것은 문제가 발생할 수 있지만 현재는 아니므로
        // fetch join으로 해결이 가능할 것으로 예상
        // 어쨋든 findByPostId() 메소드에 대한 쿼리를 수정하면 될 것 -> JPQL로 post랑 comment를 fetch join
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
