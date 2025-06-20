package com.back2basics.board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.board.post.port.in.command.PostCreateCommand;
import com.back2basics.board.post.port.out.PostCreatePort;
import com.back2basics.board.post.service.PostCreateService;
import com.back2basics.board.post.service.result.PostCreateResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostCreateService 테스트")
class PostCreateServiceTest {

    @Mock
    private PostCreatePort postCreatePort;

    @InjectMocks
    private PostCreateService postCreateService;

    @Test
    @DisplayName("게시글 생성 성공")
    void createPost_Success() {
        // given
        PostCreateCommand command = PostCreateCommand.builder()
            .authorId(1L)
            .title("테스트 제목")
            .content("테스트 내용")
            .type(PostType.GENERAL)
            .priority(1)
            .build();

        // given(postCreatePort.save(any(Post.class))).willReturn(1L);
        doNothing().when(postCreatePort).save(any(Post.class));
        // todo: port는 void인데 코어쪽에서 result 반환하고있음 -> 이거 수정해야함

        // when
        PostCreateResult result = postCreateService.createPost(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("테스트 제목");
        assertThat(result.getContent()).isEqualTo("테스트 내용");
        assertThat(result.getType()).isEqualTo(PostType.GENERAL);
        assertThat(result.getPriority()).isEqualTo(1);
        assertThat(result.getAuthorId()).isEqualTo(1L);

        // ArgumentCaptor로 전달된 Post 객체 검증
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postCreatePort).save(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        assertThat(capturedPost.getAuthorId()).isEqualTo(1L);
        assertThat(capturedPost.getTitle()).isEqualTo("테스트 제목");
        assertThat(capturedPost.getContent()).isEqualTo("테스트 내용");
        assertThat(capturedPost.getType()).isEqualTo(PostType.GENERAL);
        assertThat(capturedPost.getPriority()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 생성 시 기본 상태값이 PENDING으로 설정됨")
    void createPost_DefaultStatusPending() {
        // given
        PostCreateCommand command = PostCreateCommand.builder()
            .authorId(1L)
            .title("테스트 제목")
            .content("테스트 내용")
            .type(PostType.NOTICE)
            .priority(2)
            .build();

        // given(postCreatePort.save(any(Post.class))).willReturn(2L);
        doNothing().when(postCreatePort).save(any(Post.class));
        // todo: port는 void인데 코어쪽에서 result 반환하고있음 -> 이거 수정해야함

        // when
        PostCreateResult result = postCreateService.createPost(command);

        // then
        assertThat(result.getStatus()).isEqualTo(PostPriority.PENDING);

        // ArgumentCaptor로 저장된 Post의 상태 검증
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postCreatePort).save(postCaptor.capture());

        Post capturedPost = postCaptor.getValue();
        assertThat(capturedPost.getStatus()).isEqualTo(PostPriority.PENDING);
    }
}