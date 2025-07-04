package com.back2basics.board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.board.post.port.out.PostSoftDeletePort;
import com.back2basics.board.post.service.PostDeleteService;
import com.back2basics.infra.validator.PostValidator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostDeleteService 테스트")
class PostDeleteServiceTest {

    @Mock
    private PostValidator postValidator;

    @Mock
    private PostSoftDeletePort postSoftDeletePort;

    @InjectMocks
    private PostDeleteService postDeleteService;

    @Test
    @DisplayName("게시글 삭제 성공")
    void deletePost_Success() {
        // given
        Long postId = 1L;
        Long requesterId = 1L;

        Post post = Post.builder()
            .id(postId)
            .authorId(requesterId)
            .title("삭제할 게시글")
            .content("삭제할 내용")
            .type(PostType.GENERAL)
            .status(PostPriority.PENDING)
            .priority(1)
            .createdAt(LocalDateTime.now())
            .build();

        given(postValidator.findPost(postId)).willReturn(post);

        // when
        postDeleteService.softDeletePost(postId, requesterId);

        // then
        verify(postValidator).findPost(postId);
        verify(postValidator).isAuthor(post, requesterId);

        // ArgumentCaptor로 삭제된 Post 객체 검증
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postSoftDeletePort).softDelete(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        assertThat(capturedPost.getId()).isEqualTo(postId);
        assertThat(capturedPost.getAuthorId()).isEqualTo(requesterId);
        assertThat(capturedPost.getTitle()).isEqualTo("삭제할 게시글");
        assertThat(capturedPost.getContent()).isEqualTo("삭제할 내용");
    }

    @Test
    @DisplayName("다른 사용자의 게시글 삭제 시도")
    void deletePost_DifferentAuthor() {
        // given
        Long postId = 2L;
        Long postAuthorId = 1L;
        Long requesterId = 2L;  // 다른 사용자

        Post post = Post.builder()
            .id(postId)
            .authorId(postAuthorId)
            .title("다른 사용자의 게시글")
            .content("다른 사용자의 내용")
            .type(PostType.NOTICE)
            .status(PostPriority.PENDING)
            .priority(1)
            .createdAt(LocalDateTime.now())
            .build();

        given(postValidator.findPost(postId)).willReturn(post);

        // when
        postDeleteService.softDeletePost(postId, requesterId);

        // then
        verify(postValidator).findPost(postId);
        // verify(postValidator).isAuthor(post, requesterId); // 권한 검증 실패하겠지만 행동 지정 안해주기
        // 그럼 꼭 있어야되나..?

        // ArgumentCaptor로 검증하려 했지만, 권한 검증 실패로 delete가 호출되지 않을 수 있음
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postSoftDeletePort).softDelete(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        assertThat(capturedPost.getId()).isEqualTo(postId);
    }

    @Test
    @DisplayName("삭제할 게시글의 정보가 정확히 전달됨")
    void deletePost_CorrectPostPassed() {
        // given
        Long postId = 3L;
        Long requesterId = 3L;

        Post post = Post.builder()
            .id(postId)
            .authorId(requesterId)
            .title("정확성 테스트")
            .content("정확성 테스트 내용")
            .type(PostType.GENERAL)
            .status(PostPriority.PENDING)
            .priority(5)
            .createdAt(LocalDateTime.now())
            .build();

        given(postValidator.findPost(postId)).willReturn(post);

        // when
        postDeleteService.softDeletePost(postId, requesterId);

        // then
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postSoftDeletePort).softDelete(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        assertThat(capturedPost).isEqualTo(post); // 동일한 객체인지 확인
        assertThat(capturedPost.getType()).isEqualTo(PostType.GENERAL);
        assertThat(capturedPost.getStatus()).isEqualTo(PostPriority.PENDING);
        assertThat(capturedPost.getPriority()).isEqualTo(5);
    }
}