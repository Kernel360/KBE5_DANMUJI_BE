package com.back2basics.post.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.spy;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostPersistenceAdapter 테스트")
class PostPersistenceAdapterTest {

    @Mock
    private PostEntityRepository postRepository;

    @Mock
    private PostMapper mapper;

    @InjectMocks
    private PostPersistenceAdapter postPersistenceAdapter;

    @Test
    @DisplayName("게시글 저장이 성공한다")
    void save_Success() {
        // given
        Post post = createMockPost(null);
        PostEntity entity = createMockEntity(1L);

        given(mapper.toEntity(post)).willReturn(entity);
        given(postRepository.save(entity)).willReturn(entity);

        // when
        postPersistenceAdapter.save(post);

        // then
        verify(mapper).toEntity(post);
        verify(postRepository).save(entity);
    }

    @Test
    @DisplayName("ID로 게시글 조회가 성공한다")
    void findById_Success() {
        // given
        Long postId = 1L;
        PostEntity entity = createMockEntity(postId);
        Post post = createMockPost(postId);

        given(postRepository.findById(postId)).willReturn(Optional.of(entity));
        given(mapper.toDomain(entity)).willReturn(post);

        // when
        Optional<Post> result = postPersistenceAdapter.findById(postId);

        // then
        verify(postRepository).findById(postId);
        verify(mapper).toDomain(entity);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(postId);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회 시 빈 Optional을 반환한다")
    void findById_NotFound_ReturnsEmpty() {
        // given
        Long postId = 999L;
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        // when
        Optional<Post> result = postPersistenceAdapter.findById(postId);

        // then
        verify(postRepository).findById(postId);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("모든 게시글 조회가 성공한다")
    void findAll_Success() {
        // given
        List<PostEntity> entities = Arrays.asList(
            createMockEntity(1L),
            createMockEntity(2L)
        );
        List<Post> posts = Arrays.asList(
            createMockPost(1L),
            createMockPost(2L)
        );

        given(postRepository.findAll()).willReturn(entities);
        given(mapper.toDomain(any(PostEntity.class)))
            .willReturn(posts.get(0), posts.get(1));

        // when
        List<Post> results = postPersistenceAdapter.findAll();

        // then
        verify(postRepository).findAll();
        verify(mapper, times(2)).toDomain(any(PostEntity.class));
        assertThat(results).hasSize(2);
    }

    @Test
    @DisplayName("게시글 업데이트가 성공한다")
    void update_Success() {
        // given
        Long postId = 1L;
        Post post = createMockPost(postId);
        PostEntity entity = createMockEntity(postId);

        given(postRepository.findById(postId)).willReturn(Optional.of(entity));
        given(postRepository.save(entity)).willReturn(entity);

        // when
        postPersistenceAdapter.update(post);

        // then
        verify(postRepository).findById(postId);
        verify(mapper).updateEntityFields(entity, post);
        verify(postRepository).save(entity);
    }

    @Test
    @DisplayName("존재하지 않는 게시글 업데이트 시 예외가 발생한다")
    void update_NotFound_ThrowsException() {
        // given
        Long postId = 999L;
        Post post = createMockPost(postId);

        given(postRepository.findById(postId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postPersistenceAdapter.update(post))
            .isInstanceOf(PostException.class);
    }

    @Test
    @DisplayName("게시글 소프트 삭제가 성공한다")
    void softDelete_Success() {
        // given
        Long postId = 1L;
        Post post = createMockPost(postId);
        PostEntity entity = createMockEntity(postId);

        given(postRepository.findById(postId)).willReturn(Optional.of(entity));
        given(postRepository.save(entity)).willReturn(entity);

        // when
        postPersistenceAdapter.softDelete(post);

        // then
        verify(postRepository).findById(postId);
        verify(entity).markDeleted();
        verify(postRepository).save(entity);
    }

    private Post createMockPost(Long id) {
        return Post.builder()
            .id(id)
            .authorId(1L)
            .title("테스트 제목")
            .content("테스트 내용")
            .type(PostType.GENERAL)
            .priority(1)
            .build();
    }

    private PostEntity createMockEntity(Long id) {
        return spy(PostEntity.builder()
            .id(id)
            .authorId(1L)
            .title("테스트 제목")
            .content("테스트 내용")
            .type(PostType.GENERAL)
            .priority(1)
            .build());
    }
}
