package com.back2basics.comment.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentDeletePort;
import com.back2basics.infra.validation.validator.CommentValidator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentDeleteServiceTest {

    @Mock
    private CommentValidator commentValidator;

    @Mock
    private CommentDeletePort commentDeletePort;

    @InjectMocks
    private CommentDeleteService commentDeleteService;

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteComment_Success() {
        // given
        Long commentId = 1L;
        Long requesterId = 1L;

        Comment comment = Comment.builder()
            .id(commentId)
            .authorId(requesterId)
            .content("삭제할 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        given(commentValidator.findComment(commentId)).willReturn(comment);

        // when
        commentDeleteService.deleteComment(commentId, requesterId);

        // then
        verify(commentValidator).findComment(commentId);
        verify(commentValidator).isAuthor(comment, requesterId);
        verify(commentDeletePort).delete(comment);
    }

    @Test
    @DisplayName("댓글 삭제 시 작성자 검증")
    void deleteComment_VerifyAuthor() {
        // given
        Long commentId = 1L;
        Long requesterId = 1L;

        Comment comment = Comment.builder()
            .id(commentId)
            .authorId(requesterId)
            .content("삭제할 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        given(commentValidator.findComment(commentId)).willReturn(comment);

        // when
        commentDeleteService.deleteComment(commentId, requesterId);

        // then
        verify(commentValidator).isAuthor(comment, requesterId);
    }

    @Test
    @DisplayName("댓글 삭제 시 정확한 댓글이 삭제됨")
    void deleteComment_CorrectCommentDeleted() {
        // given
        Long commentId = 1L;
        Long requesterId = 1L;

        Comment comment = Comment.builder()
            .id(commentId)
            .authorId(requesterId)
            .content("삭제할 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        given(commentValidator.findComment(commentId)).willReturn(comment);

        // when
        commentDeleteService.deleteComment(commentId, requesterId);

        // then
        verify(commentDeletePort).delete(comment);
    }
}