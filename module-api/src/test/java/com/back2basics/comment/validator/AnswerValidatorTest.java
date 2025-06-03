package com.back2basics.infra.validation.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentReadPort;
import com.back2basics.infra.exception.comment.CommentErrorCode;
import com.back2basics.infra.exception.comment.CommentException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnswerValidatorTest {

    @Mock
    private CommentReadPort commentRepository;

    @InjectMocks
    private CommentValidator commentValidator;

    @Test
    @DisplayName("댓글 조회 성공")
    void findComment_Success() {
        // given
        Long commentId = 1L;
        Comment comment = Comment.builder()
            .id(commentId)
            .authorId(1L)
            .content("테스트 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

        // when
        Comment result = commentValidator.findComment(commentId);

        // then
        assertThat(result).isEqualTo(comment);
        assertThat(result.getId()).isEqualTo(commentId);
    }

    @Test
    @DisplayName("존재하지 않는 댓글 조회 시 예외 발생")
    void findComment_NotFound_ThrowsException() {
        // given
        Long commentId = 999L;
        given(commentRepository.findById(commentId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentValidator.findComment(commentId))
            .isInstanceOf(CommentException.class)
            .hasMessage(CommentErrorCode.COMMENT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("작성자 검증 성공")
    void isAuthor_Success() {
        // given
        Long authorId = 1L;
        Comment comment = Comment.builder()
            .id(1L)
            .authorId(authorId)
            .content("테스트 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        // when & then (예외가 발생하지 않으면 성공)
        commentValidator.isAuthor(comment, authorId);
    }

    @Test
    @DisplayName("작성자가 아닌 경우 예외 발생")
    void isAuthor_NotAuthor_ThrowsException() {
        // given
        Long authorId = 1L;
        Long differentUserId = 2L;

        Comment comment = Comment.builder()
            .id(1L)
            .authorId(authorId)
            .content("테스트 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        // when & then
        assertThatThrownBy(() -> commentValidator.isAuthor(comment, differentUserId))
            .isInstanceOf(CommentException.class)
            .hasMessage(CommentErrorCode.INVALID_COMMENT_AUTHOR.getMessage());
    }

    @Test
    @DisplayName("requesterId가 null인 경우 예외 발생")
    void isAuthor_RequesterIdNull_ThrowsException() {
        // given
        Comment comment = Comment.builder()
            .id(1L)
            .authorId(1L)
            .content("테스트 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        // when & then
        assertThatThrownBy(() -> commentValidator.isAuthor(comment, null))
            .isInstanceOf(CommentException.class)
            .hasMessage(CommentErrorCode.INVALID_COMMENT_AUTHOR.getMessage());
    }
}