package com.back2basics.domain.post.controller;

import com.back2basics.domain.post.controller.code.PostResponseCode;
import com.back2basics.domain.post.dto.request.PostCreateApiRequest;
import com.back2basics.domain.post.dto.request.PostUpdateApiRequest;
import com.back2basics.domain.post.dto.response.PostCreateResponse;
import com.back2basics.domain.post.dto.response.PostReadResponse;
import com.back2basics.domain.post.swagger.PostApiDocs;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/project-steps/")
@RequiredArgsConstructor
public class PostController implements PostApiDocs {

    private final PostCreateUseCase createPostUseCase;
    private final PostReadUseCase postReadUseCase;
    private final PostUpdateUseCase postUpdateUseCase;
    private final PostDeleteUseCase postDeleteUseCase;
    private final PostSearchUseCase postSearchUseCase;

    @PostMapping("/{projectStepId}/posts")
    public ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectStepId,
        @RequestBody @Valid PostCreateApiRequest request) {

        Long userId = customUserDetails.getId();
        String userIp = customUserDetails.getIp();
        PostCreateResult result = createPostUseCase.createPost(userId, projectStepId, userIp,
            request.toCommand());
        PostCreateResponse response = PostCreateResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_CREATE_SUCCESS, response);
    }

    @GetMapping("/{projectStepId}/posts/{postId}")
    public ResponseEntity<ApiResponse<PostReadResponse>> getPost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectStepId,
        @PathVariable Long postId) {
        Long userId = customUserDetails.getId();
        PostReadResult result = postReadUseCase.getPost(userId, projectStepId, postId);
        PostReadResponse response = PostReadResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_READ_SUCCESS, response);
    }

    @GetMapping("/{projectStepId}/posts")
    public ResponseEntity<ApiResponse<Page<PostReadResponse>>> getAllPostsByProjectStep(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectStepId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Long userId = customUserDetails.getId();
        Page<PostReadResult> resultPage = postReadUseCase.getAllPostsByProjectStepId(userId,
            projectStepId, pageable);
        Page<PostReadResponse> responsePage = resultPage.map(PostReadResponse::toResponse);

        return ApiResponse.success(PostResponseCode.POST_READ_ALL_SUCCESS, responsePage);
    }


    @PutMapping("/{projectStepId}/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectStepId,
        @PathVariable Long postId,
        @Valid @RequestBody PostUpdateApiRequest request) {

        Long userId = customUserDetails.getId();
        String userIp = customUserDetails.getIp();
        postUpdateUseCase.updatePost(userId, userIp, projectStepId, postId, request.toCommand());

        return ApiResponse.success(PostResponseCode.POST_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{projectStepId}/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectStepId,
        @PathVariable Long postId) {
        Long userId = customUserDetails.getId();
        postDeleteUseCase.softDeletePost(userId, projectStepId, postId);
        return ApiResponse.success(PostResponseCode.POST_DELETE_SUCCESS);
    }

    @GetMapping("/{projectStepId}/posts/search")
    public ResponseEntity<ApiResponse<Page<PostReadResponse>>> searchPosts(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectStepId,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String clientCompany,
        @RequestParam(required = false) String developerCompany,
        @RequestParam(required = false) Integer priority,
        @RequestParam(required = false) PostStatus status,
        @RequestParam(required = false) PostType type,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostReadResult> resultPage = postSearchUseCase.searchPost(
            customUserDetails.getId(), title, clientCompany, developerCompany, author, priority,
            status, type, pageable
        );
        Page<PostReadResponse> responsePage = resultPage.map(PostReadResponse::toResponse);

        return ApiResponse.success(PostResponseCode.POST_READ_ALL_SUCCESS, responsePage);
    }
}