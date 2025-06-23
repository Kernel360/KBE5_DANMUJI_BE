package com.back2basics.board.adapter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.back2basics.adapter.persistence.board.post.PostEntity;
import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
import com.back2basics.adapter.persistence.board.post.PostMapper;
import com.back2basics.adapter.persistence.board.post.adapter.PostDeleteJpaAdapter;
import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostDeleteJpaAdapter 테스트")
class PostDeleteJpaAdapterTest {

    @Mock
    private PostEntityRepository postRepository;

    @Mock
    private PostMapper mapper;

    @InjectMocks
    private PostDeleteJpaAdapter postDeleteJpaAdapter;

    private Post post;
    private PostEntity postEntity;

    @BeforeEach
    void setUp() {
        post = Post.builder()
            .id(1L)
            .authorId(1L)
            .title("삭제할 게시글")
            .content("삭제할 내용")
            .type(PostType.GENERAL)
            .status(PostPriority.PENDING)
            .priority(1)
            .build();
        post.markDeleted(); // 삭제 마킹

        postEntity = PostEntity.builder()
            .id(1L)
            .authorId(1L)
            .title("삭제할 게시글")
            .content("삭제할 내용")
            .type(PostType.GENERAL)
            .status(PostPriority.PENDING)
            .priority(1)
            .build();
    }

    @Test
    @DisplayName("게시글 소프트 삭제 성공")
    void softDelete_Success() {
        // given
        given(postRepository.findById(post.getId())).willReturn(Optional.of(postEntity));

        // when
        postDeleteJpaAdapter.softDelete(post);

        // then
        verify(postRepository).findById(post.getId());
        verify(postRepository).save(postEntity);
        verifyNoInteractions(mapper);
    }


    @Test
    @DisplayName("게시글 소프트 삭제 실패 - 존재하지 않는 게시글")
    void softDelete_PostNotFound_ShouldThrowException() {
        // given
        given(postRepository.findById(post.getId())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postDeleteJpaAdapter.softDelete(post))
            .isInstanceOf(PostException.class)
            .hasMessageContaining(PostErrorCode.POST_NOT_FOUND.getMessage());

        verify(postRepository).findById(post.getId());
    }

    @Test
    @DisplayName("이미 삭제된 게시글도 소프트 삭제 처리 가능 - 일단 이렇게 했는데 검증 필요한지??")
    void softDelete_AlreadyDeletedPost() {
        // given
        PostEntity alreadyDeletedEntity = PostEntity.builder()
            .id(1L)
            .authorId(1L)
            .title("이미 삭제된 게시글")
            .content("이미 삭제된 내용")
            .type(PostType.GENERAL)
            .status(PostPriority.PENDING)
            .priority(1)
            .build();
        alreadyDeletedEntity.markDeleted(); // 이미 삭제된 상태

        given(postRepository.findById(post.getId())).willReturn(Optional.of(alreadyDeletedEntity));

        // when
        postDeleteJpaAdapter.softDelete(post);

        // then
        verify(postRepository).findById(post.getId());
        verify(postRepository).save(alreadyDeletedEntity);
        verifyNoInteractions(mapper);
    }
}
