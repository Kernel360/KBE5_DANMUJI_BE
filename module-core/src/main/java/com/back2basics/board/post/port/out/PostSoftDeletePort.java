package com.back2basics.board.post.port.out;

import com.back2basics.board.post.model.Post;

public interface PostSoftDeletePort {

    void softDelete(Post post);
    // todo: update와 완전히 동일한 기능인데 나누어야할지? 그래도 메소드 명으로 명확히 하는게 나을지?
}
