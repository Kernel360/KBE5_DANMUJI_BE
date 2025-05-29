package com.back2basics.post.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.adapter.persistence.post.adapter.PostReadJpaAdapter;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostReadJpaAdapter 테스트")
class PostReadJpaAdapterTest {

    @Mock
    private PostEntityRepository postRepository;

    @Mock
    private PostMapper mapper;

    @Mock
    private JPAQueryFactory queryFactory;

    @InjectMocks
    private PostReadJpaAdapter postReadJpaAdapter;

    private PostEntity postEntity1;
    private PostEntity postEntity2;
    private Post post1;
    private Post post2;

    @BeforeEach
    void setUp() {
        postEntity1 = PostEntity.builder()
            .id(1L)
            .authorId(1L)
            .title("첫 번째 게시글")
            .content("첫 번째 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        postEntity2 = PostEntity.builder()
            .id(2L)
            .authorId(2L)
            .title("두 번째 게시글")
            .content("두 번째 내용")
            .type(PostType.NOTICE)
            .status(PostStatus.PENDING)
            .priority(2)
            .build();

        post1 = Post.builder()
            .id(1L)
            .authorId(1L)
            .title("첫 번째 게시글")
            .content("첫 번째 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        post2 = Post.builder()
            .id(2L)
            .authorId(2L)
            .title("두 번째 게시글")
            .content("두 번째 내용")
            .type(PostType.NOTICE)
            .status(PostStatus.PENDING)
            .priority(2)
            .build();
    }

    @Test
    @DisplayName("ID로 댓글 포함 게시글 조회 성공")
    void findByIdWithComments_Success() {
        // given
        Long postId = 1L;
        given(postRepository.findActivePostWithComments(postId)).willReturn(
            Optional.of(postEntity1));
        given(mapper.toDomain(postEntity1)).willReturn(post1);

        // when
        Optional<Post> result = postReadJpaAdapter.findById(postId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getTitle()).isEqualTo("첫 번째 게시글");

        verify(postRepository).findActivePostWithComments(postId);
        verify(mapper).toDomain(postEntity1);
    }

    @Test
    @DisplayName("ID로 게시글 조회 - 존재하지 않는 경우")
    void findById_NotFound() {
        // given
        Long postId = 999L;
        given(postRepository.findActivePostWithComments(postId)).willReturn(Optional.empty());

        // when
        Optional<Post> result = postReadJpaAdapter.findById(postId);

        // then
        assertThat(result).isNotPresent();

        verify(postRepository).findActivePostWithComments(postId);
    }

    @Test
    @DisplayName("페이징으로 댓글 포함 게시글 목록 조회 성공")
    void findAllWithPaging_Success() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> postIds = Arrays.asList(1L, 2L);
        List<PostEntity> entities = Arrays.asList(postEntity1, postEntity2);

        given(postRepository.findActivePostIds(pageable)).willReturn(postIds);
        given(postRepository.findActivePostsWithCommentsByIds(postIds)).willReturn(entities);
        given(postRepository.countActivePosts()).willReturn(2L);
        given(mapper.toDomain(postEntity1)).willReturn(post1);
        given(mapper.toDomain(postEntity2)).willReturn(post2);

        // when
        Page<Post> result = postReadJpaAdapter.findAllWithPaging(pageable);

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2L);
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getContent().get(1).getId()).isEqualTo(2L);

        verify(postRepository).findActivePostIds(pageable);
        verify(postRepository).findActivePostsWithCommentsByIds(postIds);
        verify(postRepository).countActivePosts();
        verify(mapper).toDomain(postEntity1);
        verify(mapper).toDomain(postEntity2);
    }

    @Test
    @DisplayName("페이징 조회 - 빈 결과")
    void findAllWithPaging_EmptyResult() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> emptyIds = Arrays.asList();

        given(postRepository.findActivePostIds(pageable)).willReturn(emptyIds);
        given(postRepository.countActivePosts()).willReturn(0L);

        // when
        Page<Post> result = postReadJpaAdapter.findAllWithPaging(pageable);

        // then
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isEqualTo(0L);

        verify(postRepository).findActivePostIds(pageable);
        verify(postRepository).countActivePosts();
    }

    @Test
    @DisplayName("활성 게시글 전체 조회")
    void findAll_Success() {
        // given
        List<PostEntity> entities = Arrays.asList(postEntity1, postEntity2);
        given(postRepository.findAllActivePosts()).willReturn(entities);
        given(mapper.toDomain(postEntity1)).willReturn(post1);
        given(mapper.toDomain(postEntity2)).willReturn(post2);

        // when
        List<Post> results = postReadJpaAdapter.findAll();

        // then
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getId()).isEqualTo(1L);
        assertThat(results.get(1).getId()).isEqualTo(2L);

        verify(postRepository).findAllActivePosts();
        verify(mapper).toDomain(postEntity1);
        verify(mapper).toDomain(postEntity2);
    }
}