package com.back2basics.port.in.comment;

public interface DeleteCommentUseCase {

    void deleteComment(Long id, String requesterName);
}
