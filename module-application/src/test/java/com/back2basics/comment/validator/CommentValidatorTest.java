package com.back2basics.comment.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.back2basics.infra.comment.validation.CommentValidator;
import com.back2basics.model.comment.Comment;
import com.back2basics.port.out.comment.CommentRepositoryPort;
import com.back2basics.service.comment.exception.CommentErrorCode;
import com.back2basics.service.comment.exception.CommentException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentValidatorTest {

    @Mock
    private CommentRepositoryPort commentRepositoryPort;

    @InjectMocks
    private CommentValidator commentValidator;

    private final Long commentId = 1L;
    private Comment sampleComment;
    private final String authorName = "AAA";
    private final String another = "BBB";

    @BeforeEach
    void setUp() {
        sampleComment = Comment.builder()
            .id(commentId)
            .authorName(authorName)
            .content("content")
            .postId(1L)
            .parentId(null)
            .build();
    }

    @Test
    @DisplayName("댓글이 존재하지 않으면 예외 발생")
    void commentNotFoundThrowsException() {
        // given
        when(commentRepositoryPort.findById(commentId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentValidator.findComment(commentId))
            .isInstanceOf(CommentException.class)
            .hasMessageContaining(CommentErrorCode.COMMENT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("작성자가 다르면 예외 발생")
    void invalidAuthorThrowsException() {
        // when & then
        assertThatThrownBy(() -> commentValidator.isAuthor(sampleComment, another))
            .isInstanceOf(CommentException.class)
            .hasMessageContaining(CommentErrorCode.INVALID_COMMENT_AUTHOR.getMessage());
    }
}