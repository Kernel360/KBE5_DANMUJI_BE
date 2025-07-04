package com.back2basics.infra.validator;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentReadPort;
import com.back2basics.infra.exception.comment.CommentErrorCode;
import com.back2basics.infra.exception.comment.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidator {

    private final CommentReadPort commentRepository;

    public Comment findComment(Long id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
    }

    public void isAuthor(Comment comment, Long requesterId) {
        if (!comment.getAuthorId().equals(requesterId)) {
            throw new CommentException(CommentErrorCode.INVALID_COMMENT_AUTHOR);
        }
    }

    // 부모 댓글 존재 여부 검증
    // 부모 댓글이 존재할 경우, 그 부모 댓글의 postId가 현재 댓글이 달릴 postId와 일치하는지 검증
    public void validateParentComment(Long parentCommentId, Long targetPostId) {
        if (parentCommentId == null) {
            return;
        }

        Comment parentComment = findComment(parentCommentId);
        
        if (!parentComment.getPostId().equals(targetPostId)) {
            throw new CommentException(CommentErrorCode.INVALID_COMMENT_PARENT_POST_ID);
        }
    }
}
