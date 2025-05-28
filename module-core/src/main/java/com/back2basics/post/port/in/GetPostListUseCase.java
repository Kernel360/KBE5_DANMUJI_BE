package com.back2basics.post.port.in;

import com.back2basics.post.service.result.PostSimpleResult;
import java.util.List;

public interface GetPostListUseCase {

    List<PostSimpleResult> getPostList();

}
