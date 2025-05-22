package com.back2basics.service.post;

public interface GetPostUseCase {

    PostResponseDto getPost(Long id);
    List<PostResponseDto> getAllPosts();
}
