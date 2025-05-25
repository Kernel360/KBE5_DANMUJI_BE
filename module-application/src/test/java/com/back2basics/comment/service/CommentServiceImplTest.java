package com.back2basics.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.back2basics.infra.comment.validation.CommentValidator;
import com.back2basics.model.comment.Comment;
import com.back2basics.model.post.Post;
import com.back2basics.model.post.PostStatus;
import com.back2basics.port.out.comment.CommentRepositoryPort;
import com.back2basics.service.comment.CommentServiceImpl;
import com.back2basics.service.comment.dto.CommentCreateCommand;
import com.back2basics.service.comment.dto.CommentUpdateCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepositoryPort commentRepositoryPort;

    @Mock
    private CommentValidator commentValidator;

    @InjectMocks
    private CommentServiceImpl commentServiceImpl;

    private Comment sampleComment;
    private Comment sampleComment2;
    private Post samplePost;
    private Long postId = 1L;
    private Long parentId = 1L;
    private String commentAuthorName = "comment author";
    private String content = "content";

    @BeforeEach
    void setUp() {
        samplePost = Post.builder()
            .id(postId)
            .authorName("title author")
            .title("title")
            .content("content")
            .status(PostStatus.PENDING)
            .priority(1)
            .build();

        sampleComment = Comment.builder()
            .id(parentId)
            .authorName(commentAuthorName)
            .content(content)
            .postId(1L)
            .parentId(null)
            .build();

        sampleComment2 = Comment.builder()
            .id(2L)
            .authorName(commentAuthorName)
            .content("content2")
            .postId(1L)
            .parentId(parentId)
            .build();
    }

    @Test
    @DisplayName("댓글 생성")
    void createCommentTest() {
        // given
        CommentCreateCommand command = new CommentCreateCommand(postId, null, commentAuthorName,
            content);

        // when
        when(commentRepositoryPort.save(any(Comment.class))).thenReturn(1L);

        // then
        Long id = commentServiceImpl.createComment(command);

        assertThat(id).isEqualTo(1L);
        verify(commentRepositoryPort).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글 수정")
    void updateCommentTest() {
        // given
        CommentUpdateCommand command = new CommentUpdateCommand(commentAuthorName, "new content");

        // when
        when(commentValidator.findComment(1L)).thenReturn(sampleComment);
        doNothing().when(commentValidator).isAuthor(sampleComment, commentAuthorName);

        commentServiceImpl.updateComment(1L, command);

        // then
        assertThat(sampleComment.getContent()).isEqualTo("new content");

        verify(commentValidator).findComment(sampleComment.getId());
        verify(commentValidator).isAuthor(sampleComment, commentAuthorName);
        verify(commentRepositoryPort).update(sampleComment);
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteCommentTest() {
        // given & when
        when(commentValidator.findComment(1L)).thenReturn(sampleComment);
        doNothing().when(commentValidator).isAuthor(sampleComment, commentAuthorName);

        commentServiceImpl.deleteComment(1L, commentAuthorName);

        // then
        assertThat(sampleComment).isNotNull();

        verify(commentValidator).findComment(1L);
        verify(commentValidator).isAuthor(sampleComment, commentAuthorName);
        verify(commentRepositoryPort).delete(sampleComment);

    }
}
