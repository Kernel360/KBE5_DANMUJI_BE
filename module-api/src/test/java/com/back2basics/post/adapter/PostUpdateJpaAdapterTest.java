package com.back2basics.post.adapter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.adapter.persistence.post.adapter.PostUpdateJpaAdapter;
import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostUpdateJpaAdapter 테스트")
class PostUpdateJpaAdapterTest {

    @Mock
    private PostEntityRepository postRepository;

    @Mock
    private PostMapper mapper;

    @InjectMocks
    private PostUpdateJpaAdapter postUpdateJpaAdapter;

    private Post post;
    private PostEntity postEntity;

    @BeforeEach
    void setUp() {
        post = Post.builder()
            .id(1L)
            .authorId(1L)
            .title("수정된 제목")
            .content("수정된 내용")
            .type(PostType.NOTICE)
            .status(PostPriority.PENDING)
            .priority(2)
            .build();

        postEntity = PostEntity.builder()
            .id(1L)
            .authorId(1L)
            .title("원본 제목")
            .content("원본 내용")
            .type(PostType.GENERAL)
            .status(PostPriority.PENDING)
            .priority(1)
            .build();
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void update_Success() {
        // given
        given(postRepository.findById(post.getId())).willReturn(Optional.of(postEntity));

        // when
        postUpdateJpaAdapter.update(post);

        // then
        verify(postRepository).findById(post.getId());
        verify(mapper).updateEntityFields(postEntity, post);
        verify(postRepository).save(postEntity);
    }

    @Test
    @DisplayName("게시글 수정 실패 - 존재하지 않는 게시글")
    void update_PostNotFound_ShouldThrowException() {
        // given
        given(postRepository.findById(post.getId())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postUpdateJpaAdapter.update(post))
            .isInstanceOf(PostException.class)
            .hasMessageContaining(PostErrorCode.POST_NOT_FOUND.getMessage());

        verify(postRepository).findById(post.getId());
    }

    @Test
    @DisplayName("다른 ID의 게시글 수정")
    void update_DifferentId() {
        // given
        Post anotherPost = Post.builder()
            .id(2L)
            .authorId(1L)
            .title("다른 게시글")
            .content("다른 내용")
            .type(PostType.GENERAL)
            .status(PostPriority.PENDING)
            .priority(1)
            .build();

        PostEntity anotherEntity = PostEntity.builder()
            .id(2L)
            .authorId(1L)
            .title("기존 제목")
            .content("기존 내용")
            .type(PostType.GENERAL)
            .status(PostPriority.PENDING)
            .priority(1)
            .build();

        given(postRepository.findById(2L)).willReturn(Optional.of(anotherEntity));

        // when
        postUpdateJpaAdapter.update(anotherPost);

        // then
        verify(postRepository).findById(2L);
        verify(mapper).updateEntityFields(anotherEntity, anotherPost);
        verify(postRepository).save(anotherEntity);
    }
}