package com.back2basics.board.post.port.in;

public interface PostRestoreUseCase {
    void restorePost(Long requesterId, Long postId);
}