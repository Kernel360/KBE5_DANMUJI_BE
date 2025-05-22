package com.back2basics.port.in.post;

import com.back2basics.service.post.dto.PostResponseDto;
import java.util.List;

public interface GetPostUseCase {

    PostResponseDto getPost(Long id);
    List<PostResponseDto> getAllPosts();
}
