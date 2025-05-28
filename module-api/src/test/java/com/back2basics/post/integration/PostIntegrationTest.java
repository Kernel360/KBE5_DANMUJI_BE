package com.back2basics.post.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.back2basics.post.model.PostType;
import com.back2basics.post.port.in.CreatePostUseCase;
import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.service.result.PostCreateResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("Post 통합 테스트")
class PostIntegrationTest {

    @Autowired
    private CreatePostUseCase createPostUseCase;

    @Test
    @DisplayName("게시글 생성부터 조회까지 전체 플로우가 정상 동작한다")
    void createAndRetrievePost_Success() {
        // given
        PostCreateCommand command = PostCreateCommand.builder()
            .authorId(1L)
            .title("통합테스트 제목")
            .content("통합테스트 내용")
            .type(PostType.GENERAL)
            .priority(1)
            .build();

        // when
        PostCreateResult result = createPostUseCase.createPost(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("통합테스트 제목");
        assertThat(result.getContent()).isEqualTo("통합테스트 내용");
    }
}
