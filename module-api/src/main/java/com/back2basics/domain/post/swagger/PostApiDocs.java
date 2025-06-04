package com.back2basics.domain.post.swagger;

import com.back2basics.domain.post.dto.request.PostCreateApiRequest;
import com.back2basics.domain.post.dto.request.PostUpdateApiRequest;
import com.back2basics.domain.post.dto.response.PostCreateResponse;
import com.back2basics.domain.post.dto.response.PostReadResponse;
import com.back2basics.global.response.result.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Post", description = "게시글 관리 API")
public interface PostApiDocs {

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "게시글 생성 요청",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PostCreateApiRequest.class),
                examples = @ExampleObject(name = "게시글 생성 예시", value = PostDocsResult.POST_CREATE_REQUEST)
            )
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "게시글 생성 성공 (P201 - 게시글 생성 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(name = "성공 응답", value = PostDocsResult.POST_CREATE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터 (C002 - Invalid input type)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = PostDocsResult.INVALID_INPUT))
        )
    })
    ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
        @RequestBody @Valid PostCreateApiRequest request);

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID를 통해 특정 게시글을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "게시글 조회 성공 (P202 - 게시글 조회 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(value = PostDocsResult.POST_READ_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "게시글을 찾을 수 없음 (P001 - 게시글을 찾을 수 없습니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = PostDocsResult.POST_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<PostReadResponse>> getPost(
        @Parameter(description = "조회할 게시글 ID", required = true, example = "1") @PathVariable Long postId);

    @Operation(summary = "게시글 목록 조회 (페이징)", description = "페이징을 통해 게시글 목록을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "게시글 목록 조회 성공 (P203 - 게시글 목록 조회 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(value = PostDocsResult.POST_READ_ALL_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Page<PostReadResponse>>> getPostsWithPagingByProjectId(
        @Parameter(description = "연관된 프로젝트 ID", required = true, example = "1") @PathVariable Long projectId,
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "게시글 수정 요청",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PostUpdateApiRequest.class),
                examples = @ExampleObject(name = "게시글 수정 예시", value = PostDocsResult.POST_UPDATE_REQUEST))
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "게시글 수정 성공 (P204 - 게시글 수정 완료)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = PostDocsResult.POST_UPDATE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "게시글 수정 권한 없음 (P002 - 게시글 작성자가 아닙니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = PostDocsResult.INVALID_POST_AUTHOR))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "게시글을 찾을 수 없음 (P001 - 게시글을 찾을 수 없습니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = PostDocsResult.POST_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Void>> updatePost(
        @Parameter(description = "수정할 게시글 ID", required = true, example = "1") @PathVariable Long postId,
        @Valid @RequestBody PostUpdateApiRequest request);

    @Operation(summary = "게시글 삭제", description = "게시글을 소프트 삭제합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "게시글 삭제 요청",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PostDeleteApiRequest.class),
                examples = @ExampleObject(name = "게시글 삭제 예시", value = PostDocsResult.POST_DELETE_REQUEST))
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "게시글 삭제 성공 (P205 - 게시글 삭제 완료)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = PostDocsResult.POST_DELETE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "게시글 삭제 권한 없음 (P002 - 게시글 작성자가 아닙니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = PostDocsResult.INVALID_POST_AUTHOR))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "게시글을 찾을 수 없음 (P001 - 게시글을 찾을 수 없습니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = PostDocsResult.POST_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Void>> deletePost(
        @Parameter(description = "삭제할 게시글 ID", required = true, example = "1") @PathVariable Long postId,
        @RequestBody PostDeleteApiRequest request);

    @Operation(summary = "게시글 검색", description = "키워드를 통해 게시글을 검색합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "게시글 검색 성공 (P203 - 게시글 목록 조회 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(value = PostDocsResult.POST_SEARCH_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Page<PostReadResponse>>> searchPosts(
        @Parameter(description = "검색 키워드", example = "Spring") @RequestParam(required = false) String keyword,
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0") @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int size);
}