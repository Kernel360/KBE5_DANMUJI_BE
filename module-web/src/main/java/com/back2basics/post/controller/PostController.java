package com.back2basics.post.controller;

import com.back2basics.port.in.post.CreatePostUseCase;
import com.back2basics.port.in.post.DeletePostUseCase;
import com.back2basics.port.in.post.GetPostUseCase;
import com.back2basics.port.in.post.UpdatePostUseCase;
import com.back2basics.response.global.result.ApiResponse;
import com.back2basics.service.post.dto.PostCreateCommand;
import com.back2basics.service.post.dto.PostResponseDto;
import com.back2basics.service.post.dto.PostUpdateCommand;
import com.back2basics.service.post.response.PostResponseCode;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final CreatePostUseCase createPostUseCase;
	private final GetPostUseCase getPostUseCase;
	private final UpdatePostUseCase updatePostUseCase;
	private final DeletePostUseCase deletePostUseCase;

	@PostMapping
	public ResponseEntity<ApiResponse<Long>> createPost(
		@RequestBody @Valid PostCreateCommand command) {
		Long id = createPostUseCase.createPost(command);
		return ApiResponse.success(PostResponseCode.POST_CREATE_SUCCESS, id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PostResponseDto>> getPost(@PathVariable Long id) {
		PostResponseDto post = getPostUseCase.getPost(id);
		return ApiResponse.success(PostResponseCode.POST_READ_SUCCESS, post);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<PostResponseDto>>> getAllPosts() {
		List<PostResponseDto> posts = getPostUseCase.getAllPosts();
		return ApiResponse.success(PostResponseCode.POST_READ_ALL_SUCCESS, posts);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<Long>> updatePost(@PathVariable Long id,
		@RequestBody PostUpdateCommand command) {
		updatePostUseCase.updatePost(id, command);
		return ApiResponse.success(PostResponseCode.POST_UPDATE_SUCCESS, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Long>> deletePost(@PathVariable Long id) {
		deletePostUseCase.deletePost(id);
		return ApiResponse.success(PostResponseCode.POST_DELETE_SUCCESS, id);
	}
}