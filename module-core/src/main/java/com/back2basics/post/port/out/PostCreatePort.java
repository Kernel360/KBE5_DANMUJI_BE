package com.back2basics.post.port.out;

import com.back2basics.post.model.Post;

public interface PostCreatePort {

    Long save(Post post);
}
