package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostDetailsResult;
import com.back2basics.post.service.result.PostSimpleResult;
import java.util.List;

public interface PostReadUseCase {

    PostDetailsResult getPost(Long id);

    List<PostSimpleResult> getPostList();
}
