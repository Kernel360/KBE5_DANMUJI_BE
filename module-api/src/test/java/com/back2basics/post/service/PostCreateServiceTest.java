package com.back2basics.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.port.out.PostCreatePort;
import com.back2basics.post.service.result.PostCreateResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

        // when
        PostCreateResult result = postCreateService.createPost(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("테스트 제목");
        assertThat(result.getContent()).isEqualTo("테스트 내용");
        assertThat(result.getType()).isEqualTo(PostType.GENERAL);
        assertThat(result.getPriority()).isEqualTo(1);
        assertThat(result.getAuthorId()).isEqualTo(1L);

        verify(postCreatePort).save(any(Post.class));
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

        // when
        PostCreateResult result = postCreateService.createPost(command);

        // then
        assertThat(result.getStatus()).isEqualTo(PostStatus.PENDING);
        verify(postCreatePort).save(any(Post.class));
    }
}