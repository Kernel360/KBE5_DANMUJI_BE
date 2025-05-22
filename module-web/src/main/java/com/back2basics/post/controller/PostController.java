package com.back2basics.post.controller;

import com.back2basics.response.global.ResponseCode;
import com.back2basics.response.global.ResultResponse;
import com.back2basics.response.post.PostResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final GetPostUseCase getPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;

    @PostMapping
    public ResponseEntity<ResultResponse> createPost(@RequestBody PostCreateCommand command) {
        Long id = createPostUseCase.createPost(command);
        return ResponseEntity.ok(ResultResponse.of(PostResponseCode.POST_CREATE_SUCCESS, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getPost(@PathVariable Long id) {
        PostResponseDto post = getPostUseCase.getPost(id);
        return ResponseEntity.ok(ResultResponse.of(PostResponseCode.POST_READ_SUCCESS, post));
    }

    @GetMapping
    public ResponseEntity<ResultResponse> getAllPosts() {
        List<PostResponseDto> posts = getPostUseCase.getAllPosts();
        return ResponseEntity.ok(ResultResponse.of(PostResponseCode.POST_READ_ALL_SUCCESS, posts));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultResponse> updatePost(@PathVariable Long id,
        @RequestBody PostUpdateCommand command) {
        updatePostUseCase.updatePost(id, command);
        return ResponseEntity.ok(ResultResponse.of(PostResponseCode.POST_UPDATE_SUCCESS, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> deletePost(@PathVariable Long id) {
        deletePostUseCase.deletePost(id);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_DELETE_SUCCESS, null));
    }
}