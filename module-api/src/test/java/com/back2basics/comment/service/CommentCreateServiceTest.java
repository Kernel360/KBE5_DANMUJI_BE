package com.back2basics.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.command.CommentCreateCommand;
import com.back2basics.comment.port.out.CommentCreatePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentCreateServiceTest {

    @Mock
    private CommentCreatePort commentCreatePort;

    @Mock
    private CommentModelRelationHelper commentModelRelationHelper;

    @InjectMocks
    private CommentCreateService commentCreateService;

    @Test
    @DisplayName("댓글 생성 성공")
    void createComment_Success() {
        // given
        CommentCreateCommand command = CommentCreateCommand.builder()
            .postId(1L)
            .authorId(1L)
            .content("테스트 댓글")
            .build();

        given(commentCreatePort.save(any(Comment.class))).willReturn(1L);

        // when
        Long result = commentCreateService.createComment(command);

        // then
        assertThat(result).isEqualTo(1L);
        verify(commentModelRelationHelper).assignRelations(any(CommentCreateCommand.class),
            any(Comment.class));
        verify(commentCreatePort).save(any(Comment.class));
    }

    @Test
    @DisplayName("대댓글 생성 성공")
    void createReply_Success() {
        // given
        CommentCreateCommand command = CommentCreateCommand.builder()
            .postId(1L)
            .authorId(1L)
            .content("대댓글입니다")
            .parentId(2L)
            .build();

        given(commentCreatePort.save(any(Comment.class))).willReturn(3L);

        // when
        Long result = commentCreateService.createComment(command);

        // then
        assertThat(result).isEqualTo(3L);
        verify(commentModelRelationHelper).assignRelations(any(CommentCreateCommand.class),
            any(Comment.class));
        verify(commentCreatePort).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 생성 시 Comment 객체가 올바르게 생성된다")
    void createComment_VerifyCommentCreation() {
        // given
        CommentCreateCommand command = CommentCreateCommand.builder()
            .postId(1L)
            .authorId(1L)
            .content("테스트 댓글")
            .parentId(null)
            .build();

        given(commentCreatePort.save(any(Comment.class))).willReturn(1L);

        // when
        commentCreateService.createComment(command);

        // then
        verify(commentCreatePort).save(argThat(comment ->
            comment.getPostId().equals(1L) &&
                comment.getAuthorId().equals(1L) &&
                comment.getContent().equals("테스트 댓글") &&
                comment.getParentCommentId() == null
        ));
    }
}