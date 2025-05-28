package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostInfoResult;
import java.util.List;

public interface GetPostUseCase {

    PostInfoResult getPost(Long id);

    List<PostInfoResult> getAllPosts();
}
