package com.back2basics.comment.port.in;

public interface CommentDeleteUseCase {

    void deleteComment(Long requesterId, Long commentId);
}
