package com.back2basics.comment.service;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import com.back2basics.comment.port.out.CommentUpdatePort;
import com.back2basics.infra.validation.validator.CommentValidator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentUpdateServiceTest {

    @Mock
    private CommentValidator commentValidator;

    @Mock
    private CommentUpdatePort commentUpdatePort;

    @InjectMocks
    private CommentUpdateService commentUpdateService;

    @Test
    @DisplayName("댓글 수정 성공")
    void updateComment_Success() {
        // given
        Long commentId = 1L;
        Long requesterId = 1L;

        Comment comment = Comment.builder()
            .id(commentId)
            .authorId(requesterId)
            .content("원본 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentUpdateCommand command = CommentUpdateCommand.builder()
            .requesterId(requesterId)
            .content("수정된 댓글")
            .build();

        given(commentValidator.findComment(commentId)).willReturn(comment);

        // when
        commentUpdateService.updateComment(commentId, command);

        // then
        verify(commentValidator).findComment(commentId);
        verify(commentValidator).isAuthor(comment, requesterId);
        verify(commentUpdatePort).update(comment);
    }

    @Test
    @DisplayName("댓글 수정 시 작성자 검증")
    void updateComment_VerifyAuthor() {
        // given
        Long commentId = 1L;
        Long requesterId = 1L;

        Comment comment = Comment.builder()
            .id(commentId)
            .authorId(requesterId)
            .content("원본 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentUpdateCommand command = CommentUpdateCommand.builder()
            .requesterId(requesterId)
            .content("수정된 댓글")
            .build();

        given(commentValidator.findComment(commentId)).willReturn(comment);

        // when
        commentUpdateService.updateComment(commentId, command);

        // then
        verify(commentValidator).isAuthor(eq(comment), eq(requesterId));
    }

    @Test
    @DisplayName("댓글 수정 시 내용이 업데이트됨")
    void updateComment_ContentUpdated() {
        // given
        Long commentId = 1L;
        Long requesterId = 1L;

        Comment comment = Comment.builder()
            .id(commentId)
            .authorId(requesterId)
            .content("원본 댓글")
            .createdAt(LocalDateTime.now())
            .build();

        CommentUpdateCommand command = CommentUpdateCommand.builder()
            .requesterId(requesterId)
            .content("수정된 댓글")
            .build();

        given(commentValidator.findComment(commentId)).willReturn(comment);

        // when
        commentUpdateService.updateComment(commentId, command);

        // then
        verify(commentUpdatePort).update(argThat(updatedComment ->
            updatedComment.getContent().equals("수정된 댓글")
        ));
    }
}