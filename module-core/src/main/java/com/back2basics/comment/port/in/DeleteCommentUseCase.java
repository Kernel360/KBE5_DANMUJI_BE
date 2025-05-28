package com.back2basics.comment.port.in;

public interface DeleteCommentUseCase {

    void deleteComment(Long id, String requesterName);
}
