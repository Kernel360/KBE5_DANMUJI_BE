package com.back2basics.post.controller;

import com.back2basics.port.in.post.CreatePostUseCase;
import com.back2basics.port.in.post.DeletePostUseCase;
import com.back2basics.port.in.post.GetPostUseCase;
import com.back2basics.port.in.post.UpdatePostUseCase;
import com.back2basics.response.global.result.ResultResponse;
import com.back2basics.response.post.PostResponseCode;
import com.back2basics.service.post.dto.PostCreateCommand;
import com.back2basics.service.post.dto.PostResponseDto;
import com.back2basics.service.post.dto.PostUpdateCommand;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final GetPostUseCase getPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;

    @PostMapping
    public ResponseEntity<ResultResponse> createPost(@RequestBody @Valid PostCreateCommand command) {
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

    @PatchMapping("/{id}")
    public ResponseEntity<ResultResponse> updatePost(@PathVariable Long id,
        @RequestBody PostUpdateCommand command) {
        updatePostUseCase.updatePost(id, command);
        return ResponseEntity.ok(ResultResponse.of(PostResponseCode.POST_UPDATE_SUCCESS, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> deletePost(@PathVariable Long id) {
        deletePostUseCase.deletePost(id);
        return ResponseEntity.ok(ResultResponse.of(PostResponseCode.POST_DELETE_SUCCESS, null));
    }
}