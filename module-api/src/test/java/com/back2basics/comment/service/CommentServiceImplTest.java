//package com.back2basics.comment.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.mockito.Mockito.verify;
//
//import com.back2basics.comment.model.Comment;
//import com.back2basics.comment.port.out.CommentRepositoryPort;
//import com.back2basics.infra.validation.validator.CommentValidator;
//import com.back2basics.post.model.Post;
//import com.back2basics.post.model.PostStatus;
//import com.back2basics.post.model.PostType;
//import com.back2basics.service.comment.dto.CommentCreateCommand;
//import com.back2basics.service.comment.dto.CommentUpdateCommand;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("CommentService 테스트")
//class CommentServiceImplTest {
//
//    @Mock
//    private CommentRepositoryPort commentRepositoryPort;
//
//    @Mock
//    private CommentValidator commentValidator;
//
//    @Mock
//    private CommentModelRelationHelper commentModelRelationHelper;
//
//    @InjectMocks
//    private CommentServiceImpl commentServiceImpl;
//
//    private Comment sampleComment;
//    private Comment sampleComment2;
//    private Post samplePost;
//    private final Long postId = 1L;
//    private final Long parentId = 1L;
//    private final String commentAuthorName = "comment author";
//    private final String content = "content";
//
//    @BeforeEach
//    void setUp() {
//        samplePost = Post.builder()
//            .id(postId)
//            .authorId(1L)              // authorName -> authorId로 수정
//            .title("title")
//            .content("content")
//            .type(PostType.GENERAL)    // type 필드 추가
//            .status(PostStatus.PENDING)
//            .priority(1)
//            .build();
//
//        sampleComment = Comment.builder()
//            .id(parentId)
//            .authorName(commentAuthorName)
//            .content(content)
//            .postId(1L)
//            .parentCommentId(null)
//            .build();
//
//        sampleComment2 = Comment.builder()
//            .id(2L)
//            .authorName(commentAuthorName)
//            .content("content2")
//            .postId(1L)
//            .parentCommentId(parentId)
//            .build();
//    }
//
//    @Test
//    @DisplayName("댓글 생성")
//    void createCommentTest() {
//        // given
//        CommentCreateCommand command = CommentCreateCommand.builder()
//            .postId(postId)
//            .parentId(null)
//            .authorName(commentAuthorName)
//            .content(content)
//            .build();
//
//        given(commentRepositoryPort.save(any(Comment.class))).willReturn(1L);
//
//        // when
//        Long id = commentServiceImpl.createComment(command);
//
//        // then
//        assertThat(id).isEqualTo(1L);
//        verify(commentRepositoryPort).save(any(Comment.class));
//        verify(commentModelRelationHelper).assignRelations(any(CommentCreateCommand.class),
//            any(Comment.class));
//    }
//
//    @Test
//    @DisplayName("댓글 수정")
//    void updateCommentTest() {
//        // given
//        CommentUpdateCommand command = CommentUpdateCommand.builder()
//            .requesterName(commentAuthorName)
//            .content("new content")
//            .build();
//
//        given(commentValidator.findComment(1L)).willReturn(sampleComment);
//        willDoNothing().given(commentValidator).isAuthor(sampleComment, commentAuthorName);
//
//        // when
//        commentServiceImpl.updateComment(1L, command);
//
//        // then
//        assertThat(sampleComment.getContent()).isEqualTo("new content");
//        verify(commentValidator).findComment(1L);
//        verify(commentValidator).isAuthor(sampleComment, commentAuthorName);
//        verify(commentRepositoryPort).update(sampleComment);
//    }
//
//    @Test
//    @DisplayName("댓글 삭제")
//    void deleteCommentTest() {
//        // given
//        given(commentValidator.findComment(1L)).willReturn(sampleComment);
//        willDoNothing().given(commentValidator).isAuthor(sampleComment, commentAuthorName);
//
//        // when
//        commentServiceImpl.deleteComment(1L, commentAuthorName);
//
//        // then
//        verify(commentValidator).findComment(1L);
//        verify(commentValidator).isAuthor(sampleComment, commentAuthorName);
//        verify(commentRepositoryPort).delete(sampleComment);
//    }
//
//    @Test
//    @DisplayName("대댓글 생성")
//    void createReplyCommentTest() {
//        // given
//        CommentCreateCommand command = CommentCreateCommand.builder()
//            .postId(postId)
//            .parentId(parentId)
//            .authorName(commentAuthorName)
//            .content("reply content")
//            .build();
//
//        given(commentRepositoryPort.save(any(Comment.class))).willReturn(2L);
//
//        // when
//        Long id = commentServiceImpl.createComment(command);
//
//        // then
//        assertThat(id).isEqualTo(2L);
//        verify(commentRepositoryPort).save(any(Comment.class));
//        verify(commentModelRelationHelper).assignRelations(any(CommentCreateCommand.class),
//            any(Comment.class));
//    }
//
//    @Test
//    @DisplayName("대댓글 수정")
//    void updateReplyCommentTest() {
//        // given
//        CommentUpdateCommand command = CommentUpdateCommand.builder()
//            .requesterName(commentAuthorName)
//            .content("updated reply")
//            .build();
//
//        given(commentValidator.findComment(2L)).willReturn(sampleComment2);
//        willDoNothing().given(commentValidator).isAuthor(sampleComment2, commentAuthorName);
//
//        // when
//        commentServiceImpl.updateComment(2L, command);
//
//        // then
//        assertThat(sampleComment2.getContent()).isEqualTo("updated reply");
//        verify(commentValidator).findComment(2L);
//        verify(commentValidator).isAuthor(sampleComment2, commentAuthorName);
//        verify(commentRepositoryPort).update(sampleComment2);
//    }
//
//    @Test
//    @DisplayName("대댓글 삭제")
//    void deleteReplyCommentTest() {
//        // given
//        given(commentValidator.findComment(2L)).willReturn(sampleComment2);
//        willDoNothing().given(commentValidator).isAuthor(sampleComment2, commentAuthorName);
//
//        // when
//        commentServiceImpl.deleteComment(2L, commentAuthorName);
//
//        // then
//        verify(commentValidator).findComment(2L);
//        verify(commentValidator).isAuthor(sampleComment2, commentAuthorName);
//        verify(commentRepositoryPort).delete(sampleComment2);
//    }
//}