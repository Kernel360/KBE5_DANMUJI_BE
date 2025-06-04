package com.back2basics.domain.post.controller;

import com.back2basics.domain.post.controller.code.PostResponseCode;
import com.back2basics.domain.post.dto.request.PostCreateApiRequest;
import com.back2basics.domain.post.dto.request.PostUpdateApiRequest;
import com.back2basics.domain.post.dto.response.PostCreateResponse;
import com.back2basics.domain.post.dto.response.PostReadResponse;
import com.back2basics.domain.post.swagger.PostApiDocs;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.post.port.in.PostCreateUseCase;
import com.back2basics.post.port.in.PostDeleteUseCase;
import com.back2basics.post.port.in.PostReadUseCase;
import com.back2basics.post.port.in.PostSearchUseCase;
import com.back2basics.post.port.in.PostUpdateUseCase;
import com.back2basics.post.service.result.PostCreateResult;
import com.back2basics.post.service.result.PostReadResult;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController implements PostApiDocs {

    private final PostCreateUseCase createPostUseCase;
    private final PostReadUseCase postReadUseCase;
    private final PostUpdateUseCase postUpdateUseCase;
    private final PostDeleteUseCase postDeleteUseCase;
    private final PostSearchUseCase postSearchUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody @Valid PostCreateApiRequest request) {
        PostCreateResult result = createPostUseCase.createPost(customUserDetails.getId(),
            request.toCommand());
        PostCreateResponse response = PostCreateResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_CREATE_SUCCESS, response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostReadResponse>> getPost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId) {
        PostReadResult result = postReadUseCase.getPost(customUserDetails.getId(), postId);
        PostReadResponse response = PostReadResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_READ_SUCCESS, response);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ApiResponse<Page<PostReadResponse>>> getPostsWithPagingByProjectId(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<PostReadResult> resultPage = postReadUseCase.getPostListByProjectId(
            customUserDetails.getId(), projectId, pageable);
        Page<PostReadResponse> responsePage = resultPage.map(PostReadResponse::toResponse);

        return ApiResponse.success(PostResponseCode.POST_READ_ALL_SUCCESS, responsePage);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId,
        @Valid @RequestBody PostUpdateApiRequest request) {
        postUpdateUseCase.updatePost(customUserDetails.getId(), postId, request.toCommand());

        return ApiResponse.success(PostResponseCode.POST_UPDATE_SUCCESS);
    }

    @PutMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId,
        @RequestBody PostDeleteApiRequest request) {
        postDeleteUseCase.softDeletePost(postId, customUserDetails.getId());
        return ApiResponse.success(PostResponseCode.POST_DELETE_SUCCESS);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<PostReadResponse>>> searchPosts(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostReadResult> resultPage = postSearchUseCase.searchPost(customUserDetails.getId(),
            keyword, pageable);
        Page<PostReadResponse> responsePage = resultPage.map(PostReadResponse::toResponse);

        return ApiResponse.success(PostResponseCode.POST_READ_ALL_SUCCESS, responsePage);
    }
}