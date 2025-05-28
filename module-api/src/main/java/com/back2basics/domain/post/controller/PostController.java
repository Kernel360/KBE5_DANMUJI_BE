package com.back2basics.domain.post.controller;

import com.back2basics.domain.post.controller.code.PostResponseCode;
import com.back2basics.domain.post.dto.request.PostCreateApiRequest;
import com.back2basics.domain.post.dto.request.PostUpdateApiRequest;
import com.back2basics.domain.post.dto.response.PostCreateApiResponse;
import com.back2basics.domain.post.dto.response.PostDetailsApiResponse;
import com.back2basics.domain.post.dto.response.PostSimpleApiResponse;
import com.back2basics.domain.post.dto.response.PostUpdateApiResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.post.port.in.CreatePostUseCase;
import com.back2basics.post.port.in.DeletePostUseCase;
import com.back2basics.post.port.in.GetPostDetailsUseCase;
import com.back2basics.post.port.in.GetPostListUseCase;
import com.back2basics.post.port.in.UpdatePostUseCase;
import com.back2basics.post.port.in.command.PostSoftDeleteCommand;
import com.back2basics.post.service.result.PostCreateResult;
import com.back2basics.post.service.result.PostDetailsResult;
import com.back2basics.post.service.result.PostSimpleResult;
import com.back2basics.post.service.result.PostUpdateResult;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final GetPostDetailsUseCase getPostDetailsUseCase;
    private final GetPostListUseCase getPostListUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<PostCreateApiResponse>> createPost(
        @RequestBody @Valid PostCreateApiRequest request) {
        PostCreateResult result = createPostUseCase.createPost(request.toCommand());
        PostCreateApiResponse response = PostCreateApiResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_CREATE_SUCCESS, response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailsApiResponse>> getPost(@PathVariable Long postId) {
        PostDetailsResult result = getPostDetailsUseCase.getPost(postId);
        PostDetailsApiResponse response = PostDetailsApiResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_READ_SUCCESS, response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostSimpleApiResponse>>> getAllPosts() {
        List<PostSimpleResult> resultList = getPostListUseCase.getPostList();
        List<PostSimpleApiResponse> responseList = resultList.stream()
            .map(PostSimpleApiResponse::toResponse)
            .collect(Collectors.toList());

        return ApiResponse.success(PostResponseCode.POST_READ_ALL_SUCCESS, responseList);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostUpdateApiResponse>> updatePost(@PathVariable Long postId,
        @Valid @RequestBody PostUpdateApiRequest request) {
        PostUpdateResult result = updatePostUseCase.updatePost(postId, request.toCommand());
        PostUpdateApiResponse response = PostUpdateApiResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_UPDATE_SUCCESS, response);
    }

    @PutMapping("delete/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId,
        @RequestBody PostSoftDeleteCommand command) {
        deletePostUseCase.softDeletePost(postId, command.getRequesterId());
        return ApiResponse.success(PostResponseCode.POST_DELETE_SUCCESS);
    }
}