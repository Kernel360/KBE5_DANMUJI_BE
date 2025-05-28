package com.back2basics.post.port.out;

import com.back2basics.post.model.Post;

public interface PostWritePort {

    Long save(Post post);

    void update(Post post);

    void softDelete(Long id);

}
