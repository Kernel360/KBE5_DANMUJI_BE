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

@ExtendWith(MockitoExtension.class)
@DisplayName("PostReadJpaAdapter 테스트")
class PostReadJpaAdapterTest {

    @Mock
    private PostEntityRepository postRepository;

    @Mock
    private PostMapper mapper;

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
    @DisplayName("ID로 게시글 조회 성공")
    void findById_Success() {
        // given
        Long postId = 1L;
        given(postRepository.findById(postId)).willReturn(Optional.of(postEntity1));
        given(mapper.toDomain(postEntity1)).willReturn(post1);

        // when
        Optional<Post> result = postReadJpaAdapter.findById(postId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getTitle()).isEqualTo("첫 번째 게시글");

        verify(postRepository).findById(postId);
        verify(mapper).toDomain(postEntity1);
    }

    @Test
    @DisplayName("ID로 게시글 조회 - 존재하지 않는 경우")
    void findById_NotFound() {
        // given
        Long postId = 999L;
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        // when
        Optional<Post> result = postReadJpaAdapter.findById(postId);

        // then
        assertThat(result).isNotPresent();

        verify(postRepository).findById(postId);
    }

    @Test
    @DisplayName("모든 게시글 조회 성공")
    void findAll_Success() {
        // given
        List<PostEntity> entities = Arrays.asList(postEntity1, postEntity2);
        given(postRepository.findAll()).willReturn(entities);
        given(mapper.toDomain(postEntity1)).willReturn(post1);
        given(mapper.toDomain(postEntity2)).willReturn(post2);

        // when
        List<Post> results = postReadJpaAdapter.findAll();

        // then
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getId()).isEqualTo(1L);
        assertThat(results.get(0).getTitle()).isEqualTo("첫 번째 게시글");
        assertThat(results.get(1).getId()).isEqualTo(2L);
        assertThat(results.get(1).getTitle()).isEqualTo("두 번째 게시글");

        verify(postRepository).findAll();
        verify(mapper).toDomain(postEntity1);
        verify(mapper).toDomain(postEntity2);
    }

    @Test
    @DisplayName("모든 게시글 조회 - 빈 리스트")
    void findAll_EmptyList() {
        // given
        given(postRepository.findAll()).willReturn(Arrays.asList());

        // when
        List<Post> results = postReadJpaAdapter.findAll();

        // then
        assertThat(results).isEmpty();

        verify(postRepository).findAll();
    }
}