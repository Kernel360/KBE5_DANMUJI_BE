package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostReadResult;
import java.util.List;

public interface PostReadUseCase {

    PostReadResult getPost(Long id);

    List<PostReadResult> getPostList();
}
