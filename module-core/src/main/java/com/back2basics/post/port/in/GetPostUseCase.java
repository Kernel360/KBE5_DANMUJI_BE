package com.back2basics.post.port.in;

import com.back2basics.service.post.dto.PostResponseDto;
import java.util.List;

public interface GetPostUseCase {

    PostResponseDto getPost(Long id);

    List<PostResponseDto> getAllPosts();
}
