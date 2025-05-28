package com.back2basics.post.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.port.out.PostRepositoryPort;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostValidatorTest {

    @Mock
    private PostRepositoryPort postRepository;

    @InjectMocks
    private PostValidator postValidator;

    private final Long postId = 1L;
    private Post samplePost;
    private final String author = "author";
    private final String another = "aaa";

    @BeforeEach
    void setUp() {
        samplePost = Post.builder()
            .id(1L)
            .authorName("author")
            .title("title")
            .content("content")
            .status(PostStatus.PENDING)
            .priority(1)
            .build();
    }

    @Test
    @DisplayName("없는 ID로 조회하면 예외 발생")
    void PostNotFoundExceptionThrow() {
        // given
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postValidator.findPost(postId))
            .isInstanceOf(PostException.class)
            .hasMessage(PostErrorCode.POST_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("작성자 다르면 예외 발생")
    void InvalidAuthorExceptionThrow() {
        // when & then
        assertThatThrownBy(() -> postValidator.isAuthor(samplePost, another))
            .isInstanceOf(PostException.class)
            .hasMessage(PostErrorCode.INVALID_POST_AUTHOR.getMessage());
    }
}
