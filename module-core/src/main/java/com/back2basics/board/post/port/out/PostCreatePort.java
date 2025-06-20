package com.back2basics.board.post.port.out;

import com.back2basics.board.post.model.Post;

public interface PostCreatePort {

    Post save(Post post);
}
