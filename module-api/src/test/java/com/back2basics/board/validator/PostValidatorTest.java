package com.back2basics.board.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.back2basics.infra.exception.post.PostErrorCode;
import com.back2basics.infra.exception.post.PostException;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.board.post.port.out.PostReadPort;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostValidator 테스트")
class PostValidatorTest {

    @Mock
    private PostReadPort postReadPort;

    @InjectMocks
    private PostValidator postValidator;

    private final Long postId = 1L;
    private Post samplePost;
    private final Long authorId = 1L;
    private final Long anotherId = 2L;

    @BeforeEach
    void setUp() {
        samplePost = Post.builder()
            .id(1L)
            .authorId(authorId)
            .title("title")
            .content("content")
            .type(PostType.GENERAL)
            .status(PostPriority.PENDING)
            .priority(1)
            .build();
    }

    @Test
    @DisplayName("없는 ID로 조회하면 예외 발생")
    void PostNotFoundExceptionThrow() {
        // given
        given(postReadPort.findById(postId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postValidator.findPost(postId))
            .isInstanceOf(PostException.class)
            .hasMessage(PostErrorCode.POST_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("게시글이 존재하면 정상적으로 반환")
    void findPost_Success() {
        // given
        given(postReadPort.findById(postId)).willReturn(Optional.of(samplePost));

        // when
        Post result = postValidator.findPost(postId);

        // then
        assertThat(result).isEqualTo(samplePost);
    }

    @Test
    @DisplayName("작성자가 다르면 예외 발생")
    void InvalidAuthorExceptionThrow() {
        // when & then
        assertThatThrownBy(() -> postValidator.isAuthor(samplePost, anotherId))
            .isInstanceOf(PostException.class)
            .hasMessage(PostErrorCode.INVALID_POST_AUTHOR.getMessage());
    }

    @Test
    @DisplayName("작성자가 같으면 예외가 발생하지 않음")
    void isAuthor_Success() {
        // when & then
        // 예외가 발생하지 않아야 함
        postValidator.isAuthor(samplePost, authorId);
    }
}