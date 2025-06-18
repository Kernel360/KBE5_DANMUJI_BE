package com.back2basics.domain.post.controller;

import com.back2basics.domain.post.controller.code.PostResponseCode;
import com.back2basics.domain.post.dto.request.PostCreateRequest;
import com.back2basics.domain.post.dto.request.PostSearchRequest;
import com.back2basics.domain.post.dto.request.PostUpdateRequest;
import com.back2basics.domain.post.dto.response.PostCreateResponse;
import com.back2basics.domain.post.dto.response.PostDetailReadResponse;
import com.back2basics.domain.post.dto.response.PostSummaryReadResponse;
import com.back2basics.domain.post.dto.response.ReadRecentPostResponse;
import com.back2basics.domain.post.swagger.PostApiDocs;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.post.file.FileDownloadResult;
import com.back2basics.post.file.FileDownloadUseCase;
import com.back2basics.post.port.in.PostCreateUseCase;
import com.back2basics.post.port.in.PostDeleteUseCase;
import com.back2basics.post.port.in.PostReadUseCase;
import com.back2basics.post.port.in.PostSearchUseCase;
import com.back2basics.post.port.in.PostUpdateUseCase;
import com.back2basics.post.service.result.PostCreateResult;
import com.back2basics.post.service.result.PostDetailReadResult;
import com.back2basics.post.service.result.PostSummaryReadResult;
import com.back2basics.post.service.result.ReadRecentPostResult;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


// todo : 이거 어케 restful하게 해야알지 너무 고민.. 어떤건 pathvaraible, 어떤건 dto, 또 어떤건 경로자체가 이상...
@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController implements PostApiDocs {

    private final PostCreateUseCase createPostUseCase;
    private final PostReadUseCase postReadUseCase;
    private final PostUpdateUseCase postUpdateUseCase;
    private final PostDeleteUseCase postDeleteUseCase;
    private final PostSearchUseCase postSearchUseCase;
    private final FileDownloadUseCase fileDownloadUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestPart("data") @Valid PostCreateRequest request,
        @RequestPart(value = "files", required = false) List<MultipartFile> files)
        throws IOException {

        Long userId = customUserDetails.getId();
        String userIp = customUserDetails.getIp();
        PostCreateResult result = createPostUseCase.createPost(
            userId,
            request.getProjectId(),
            request.getStepId(),
            userIp,
            request.toCommand(),
            files);
        PostCreateResponse response = PostCreateResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_CREATE_SUCCESS, response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailReadResponse>> getPostByProjectStep(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId) {
        Long userId = customUserDetails.getId();
        PostDetailReadResult result = postReadUseCase.getPostById(userId, postId);
        PostDetailReadResponse response = PostDetailReadResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.POST_READ_SUCCESS, response);
    }

    @GetMapping("/project/{projectId}/steps/{projectStepId}")
    public ResponseEntity<ApiResponse<Page<PostSummaryReadResponse>>> getAllPostsByProjectStep(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectId,
        @PathVariable Long projectStepId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Long userId = customUserDetails.getId();
        Page<PostSummaryReadResult> resultPage = postReadUseCase.getAllPostsByProjectIdAndStepId(
            userId,
            projectId,
            projectStepId,
            pageable);
        Page<PostSummaryReadResponse> responsePage = resultPage.map(
            PostSummaryReadResponse::toResponse);

        return ApiResponse.success(PostResponseCode.POST_READ_ALL_SUCCESS, responsePage);
    }


    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId,
        @RequestPart("data") @Valid PostUpdateRequest request,
        @RequestPart(value = "files", required = false) List<MultipartFile> files)
        throws IOException {

        Long userId = customUserDetails.getId();
        String userIp = customUserDetails.getIp();
        postUpdateUseCase.updatePost(userId, userIp, postId, request.toCommand(), files);

        return ApiResponse.success(PostResponseCode.POST_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId) {
        Long userId = customUserDetails.getId();
        postDeleteUseCase.softDeletePost(userId, postId);
        return ApiResponse.success(PostResponseCode.POST_DELETE_SUCCESS);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<PostDetailReadResponse>>> searchPosts(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Valid @ModelAttribute PostSearchRequest request,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostDetailReadResult> resultPage = postSearchUseCase.searchPost(
            customUserDetails.getId(), request.toCommand(), pageable);
        Page<PostDetailReadResponse> responsePage = resultPage.map(
            PostDetailReadResponse::toResponse);

        return ApiResponse.success(PostResponseCode.POST_READ_ALL_SUCCESS, responsePage);
    }

    @GetMapping("/recent-posts")
    public ResponseEntity<ApiResponse<List<ReadRecentPostResponse>>> getRecentPosts(
    ) {
        List<ReadRecentPostResult> results = postReadUseCase.getRecentPosts();
        List<ReadRecentPostResponse> responseList = ReadRecentPostResponse.toResponse(results);

        return ApiResponse.success(PostResponseCode.POST_READ_ALL_SUCCESS, responseList);
    }

    @GetMapping("/{postId}/files/{fileId}")
    public ResponseEntity<byte[]> downloadFile(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId,
        @PathVariable Long fileId
    ) throws IOException {
        FileDownloadResult result = fileDownloadUseCase.downloadFile(
            customUserDetails.getId(),
            postId,
            fileId
        );

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + result.fileName() + "\"")
            .header(HttpHeaders.CONTENT_TYPE, result.fileType())
            .body(result.bytes());
    }


}