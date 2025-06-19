package com.back2basics.domain.post.swagger;

import com.back2basics.domain.post.dto.request.PostCreateRequest;
import com.back2basics.domain.post.dto.request.PostSearchRequest;
import com.back2basics.domain.post.dto.request.PostUpdateRequest;
import com.back2basics.domain.post.dto.response.PostCreateResponse;
import com.back2basics.domain.post.dto.response.PostDetailReadResponse;
import com.back2basics.domain.post.dto.response.PostSummaryReadResponse;
import com.back2basics.domain.post.dto.response.ReadRecentPostResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Post", description = "게시글 관리 API")
public interface PostApiDocs {

    @Operation(
        summary = "게시글 생성",
        description = "게시글 데이터(JSON)와 첨부파일(Multipart)을 함께 전송"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        content = @Content(
            mediaType = "multipart/form-data",
            schema = @Schema(type = "object"),
            encoding = {
                @Encoding(name = "data", contentType = "application/json"),
                @Encoding(name = "files", contentType = "application/octet-stream")
            }
        )
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "게시글 생성 성공",
            content = @Content(
                examples = @ExampleObject(value = PostDocsResult.POST_CREATE_SUCCESS)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "잘못된 입력값",
            content = @Content(
                examples = @ExampleObject(value = PostDocsResult.INVALID_INPUT)
            )
        )
    })
    ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestPart("data") @Valid PostCreateRequest request,
        @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException;

    @Operation(summary = "게시글 단건 조회")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.POST_READ_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "게시글 없음",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.POST_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<PostDetailReadResponse>> getPostByProjectStep(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId);

    @Operation(summary = "게시글 목록 조회")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "목록 조회 성공",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.POST_READ_ALL_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Page<PostSummaryReadResponse>>> getAllPostsByProjectStep(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectId,
        @PathVariable Long projectStepId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "게시글 수정",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "multipart/form-data",
                schema = @Schema(implementation = PostUpdateRequest.class)
            )
        )
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "수정 성공",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.POST_UPDATE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "작성자 아님",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.INVALID_POST_AUTHOR))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "게시글 없음",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.POST_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Void>> updatePost(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId,
        @RequestPart("data") @Valid PostUpdateRequest request,
        @RequestPart(value = "files", required = false) List<MultipartFile> files)
        throws IOException;

    @Operation(summary = "게시글 삭제")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "삭제 성공",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.POST_DELETE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "작성자 아님",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.INVALID_POST_AUTHOR))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "게시글 없음",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.POST_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Void>> deletePost(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId);

    @Operation(summary = "게시글 검색")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "검색 성공",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.POST_SEARCH_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Page<PostDetailReadResponse>>> searchPosts(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Valid @ModelAttribute PostSearchRequest request,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "최근 게시글 목록 조회")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "최근 게시글 조회 성공",
            content = @Content(examples = @ExampleObject(value = PostDocsResult.POST_READ_ALL_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<List<ReadRecentPostResponse>>> getRecentPosts();
}