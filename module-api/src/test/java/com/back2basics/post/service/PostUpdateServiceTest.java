package com.back2basics.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.in.command.PostUpdateCommand;
import com.back2basics.post.port.out.PostUpdatePort;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostUpdateService 테스트")
class PostUpdateServiceTest {

    @Mock
    private PostValidator postValidator;

    @Mock
    private PostUpdatePort postUpdatePort;

    @InjectMocks
    private PostUpdateService postUpdateService;

    @Test
    @DisplayName("게시글 수정 성공")
    void updatePost_Success() {
        // given
        Long postId = 1L;
        Long requesterId = 1L;

        Post existingPost = Post.builder()
            .id(postId)
            .authorId(requesterId)
            .title("기존 제목")
            .content("기존 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .createdAt(LocalDateTime.now())
            .build();

        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(requesterId)
            .title("수정된 제목")
            .content("수정된 내용")
            .type(PostType.NOTICE)
            .status(PostStatus.PENDING)
            .priority(2)
            .build();

        given(postValidator.findPost(postId)).willReturn(existingPost);

        // when
        postUpdateService.updatePost(postId, command);

        // then
        verify(postValidator).findPost(postId);
        verify(postValidator).isAuthor(existingPost, requesterId);

        // ArgumentCaptor로 수정된 Post 객체 검증
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postUpdatePort).update(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        assertThat(capturedPost.getId()).isEqualTo(postId);
        assertThat(capturedPost.getTitle()).isEqualTo("수정된 제목");
        assertThat(capturedPost.getContent()).isEqualTo("수정된 내용");
        assertThat(capturedPost.getType()).isEqualTo(PostType.NOTICE);
        assertThat(capturedPost.getStatus()).isEqualTo(PostStatus.PENDING);
        assertThat(capturedPost.getPriority()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 수정 시 작성자 검증")
    void updatePost_VerifyAuthor() {
        // given
        Long postId = 1L;
        Long requesterId = 1L;

        Post existingPost = Post.builder()
            .id(postId)
            .authorId(requesterId)
            .title("제목")
            .content("내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .createdAt(LocalDateTime.now())
            .build();

        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(requesterId)
            .title("새 제목")
            .content("새 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        given(postValidator.findPost(postId)).willReturn(existingPost);

        // when
        postUpdateService.updatePost(postId, command);

        // then
        verify(postValidator).isAuthor(existingPost, requesterId);
    }

    @Test
    @DisplayName("게시글 수정 시 Post 객체가 올바르게 업데이트됨")
    void updatePost_VerifyPostUpdate() {
        // given
        Long postId = 2L;
        Long requesterId = 2L;

        Post existingPost = Post.builder()
            .id(postId)
            .authorId(requesterId)
            .title("원본 제목")
            .content("원본 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .createdAt(LocalDateTime.now())
            .build();

        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(requesterId)
            .title("변경된 제목")
            .content("변경된 내용")
            .type(PostType.NOTICE)
            .status(PostStatus.PENDING)
            .priority(3)
            .build();

        given(postValidator.findPost(postId)).willReturn(existingPost);

        // when
        postUpdateService.updatePost(postId, command);

        // then
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postUpdatePort).update(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        assertThat(capturedPost.getAuthorId()).isEqualTo(requesterId);
        assertThat(capturedPost.getTitle()).isEqualTo("변경된 제목");
        assertThat(capturedPost.getContent()).isEqualTo("변경된 내용");
        assertThat(capturedPost.getType()).isEqualTo(PostType.NOTICE);
        assertThat(capturedPost.getStatus()).isEqualTo(PostStatus.PENDING);
        assertThat(capturedPost.getPriority()).isEqualTo(3);
    }

    @Test
    @DisplayName("게시글 부분 수정")
    void updatePost_PartialUpdate() {
        // given
        Long postId = 3L;
        Long requesterId = 3L;

        Post existingPost = Post.builder()
            .id(postId)
            .authorId(requesterId)
            .title("기존 제목")
            .content("기존 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .createdAt(LocalDateTime.now())
            .build();

        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(requesterId)
            .title("수정된 제목만")
            .content("기존 내용")  // 내용은 그대로
            .type(PostType.GENERAL)  // 타입도 그대로
            .status(PostStatus.APPROVED)  // 상태만 변경
            .priority(1)  // 우선순위도 그대로
            .build();

        given(postValidator.findPost(postId)).willReturn(existingPost);

        // when
        postUpdateService.updatePost(postId, command);

        // then
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postUpdatePort).update(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        assertThat(capturedPost.getTitle()).isEqualTo("수정된 제목만");
        assertThat(capturedPost.getContent()).isEqualTo("기존 내용");
        assertThat(capturedPost.getType()).isEqualTo(PostType.GENERAL);
        assertThat(capturedPost.getStatus()).isEqualTo(PostStatus.APPROVED);
        assertThat(capturedPost.getPriority()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 수정 시 원본 Post 객체가 전달됨")
    void updatePost_OriginalPostPassed() {
        // given
        Long postId = 4L;
        Long requesterId = 4L;

        Post existingPost = Post.builder()
            .id(postId)
            .authorId(requesterId)
            .title("원본")
            .content("원본 내용")
            .type(PostType.GENERAL)
            .status(PostStatus.PENDING)
            .priority(1)
            .createdAt(LocalDateTime.now())
            .build();

        PostUpdateCommand command = PostUpdateCommand.builder()
            .requesterId(requesterId)
            .title("변경")
            .content("변경 내용")
            .type(PostType.NOTICE)
            .status(PostStatus.PENDING)
            .priority(2)
            .build();

        given(postValidator.findPost(postId)).willReturn(existingPost);

        // when
        postUpdateService.updatePost(postId, command);

        // then
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postUpdatePort).update(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        // 같은 객체 인스턴스가 전달되었는지 확인
        assertThat(capturedPost).isSameAs(existingPost);
        assertThat(capturedPost.getId()).isEqualTo(postId);
        assertThat(capturedPost.getAuthorId()).isEqualTo(requesterId);
    }
}