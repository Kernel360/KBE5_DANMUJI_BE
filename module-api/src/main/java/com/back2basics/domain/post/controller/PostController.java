package com.back2basics.domain.post.controller;

import com.back2basics.domain.post.controller.code.PostResponseCode;
import com.back2basics.domain.post.dto.request.PostCreateApiRequest;
import com.back2basics.domain.post.dto.request.PostDeleteApiRequest;
import com.back2basics.domain.post.dto.request.PostUpdateApiRequest;
import com.back2basics.domain.post.dto.response.PostCreateResponse;
import com.back2basics.domain.post.dto.response.PostReadResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.post.port.in.PostCreateUseCase;
import com.back2basics.post.port.in.PostDeleteUseCase;
import com.back2basics.post.port.in.PostReadUseCase;
import com.back2basics.post.port.in.PostUpdateUseCase;
import com.back2basics.post.service.result.PostCreateResult;
import com.back2basics.post.service.result.PostReadResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final PostCreateUseCase createPostUseCase;
    private final PostReadUseCase postReadUseCase;
    private final PostUpdateUseCase postUpdateUseCase;
    private final PostDeleteUseCase postDeleteUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
        @RequestBody @Valid PostCreateApiRequest request) {
        PostCreateResult result = createPostUseCase.createPost(request.toCommand());
        PostCreateResponse response = PostCreateResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_CREATE_SUCCESS, response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostReadResponse>> getPost(@PathVariable Long postId) {
        PostReadResult result = postReadUseCase.getPost(postId);
        PostReadResponse response = PostReadResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_READ_SUCCESS, response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<PostReadResponse>>> getPostsByPaging(Pageable pageable) {
        Page<PostReadResult> resultPage = postReadUseCase.getPostList(pageable);
        Page<PostReadResponse> responsePage = resultPage.map(PostReadResponse::toResponse);

        return ApiResponse.success(PostResponseCode.POST_READ_ALL_SUCCESS, responsePage);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long postId,
        @Valid @RequestBody PostUpdateApiRequest request) {
        postUpdateUseCase.updatePost(postId, request.toCommand());

        return ApiResponse.success(PostResponseCode.POST_UPDATE_SUCCESS);
    }

    @PutMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId,
        @RequestBody PostDeleteApiRequest request) {
        postDeleteUseCase.softDeletePost(postId, request.toCommand().getRequesterId());
        return ApiResponse.success(PostResponseCode.POST_DELETE_SUCCESS);
    }
}