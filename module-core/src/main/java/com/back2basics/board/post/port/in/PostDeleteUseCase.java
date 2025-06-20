package com.back2basics.board.post.port.in;

public interface PostDeleteUseCase {

    void softDeletePost(Long userId, Long postId);
}