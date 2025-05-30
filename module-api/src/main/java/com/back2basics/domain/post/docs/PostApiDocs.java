package com.back2basics.docs;

import com.back2basics.domain.post.dto.request.PostCreateApiRequest;
import com.back2basics.domain.post.dto.request.PostDeleteApiRequest;
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
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Post", description = "게시글 관리 API")
public interface PostApiDocs {

    @Operation(
        summary = "게시글 생성",
        description = "새로운 게시글을 생성합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "게시글 생성 요청",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PostCreateApiRequest.class),
                examples = @ExampleObject(
                    name = "게시글 생성 예시",
                    value = """
                        {
                          "authorId": 1,
                          "title": "새로운 게시글 제목",
                          "content": "게시글 내용입니다.",
                          "type": "GENERAL",
                          "status": "PENDING",
                          "priority": 1
                        }
                        """
                )
            )
        )
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "201",
            description = "게시글 생성 성공 (P201 - 게시글 생성 완료)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(
                    name = "성공 응답",
                    value = """
                        {
                          "success": true,
                          "code": "P201",
                          "message": "게시글 생성 완료",
                          "data": {
                            "id": 1,
                            "title": "새로운 게시글 제목"
                          }
                        }
                        """
                )
            )
        ),
        @SwaggerApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터 (C002 - Invalid input type)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "success": false,
                          "code": "C002",
                          "message": "Invalid input type",
                          "data": null
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<ApiResponse<PostCreateResponse>> createPost(
        @RequestBody @Valid PostCreateApiRequest request
    );

    @Operation(
        summary = "게시글 단건 조회",
        description = "게시글 ID를 통해 특정 게시글을 조회합니다."
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "200",
            description = "게시글 조회 성공 (P202 - 게시글 조회 완료)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(
                    value = """
                        {
                          "success": true,
                          "code": "P202",
                          "message": "게시글 조회 완료",
                          "data": {
                            "id": 1,
                            "authorId": 1,
                            "title": "게시글 제목",
                            "content": "게시글 내용",
                            "type": "GENERAL",
                            "status": "PENDING",
                            "priority": 1,
                            "createdAt": "2024-01-01T10:00:00",
                            "updatedAt": "2024-01-01T10:00:00"
                          }
                        }
                        """
                )
            )
        ),
        @SwaggerApiResponse(
            responseCode = "404",
            description = "게시글을 찾을 수 없음 (P001 - 게시글을 찾을 수 없습니다)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "success": false,
                          "code": "P001",
                          "message": "게시글을 찾을 수 없습니다",
                          "data": null
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<ApiResponse<PostReadResponse>> getPost(
        @Parameter(description = "조회할 게시글 ID", required = true, example = "1")
        @PathVariable Long postId
    );

    @Operation(
        summary = "게시글 목록 조회 (페이징)",
        description = "페이징을 통해 게시글 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "200",
            description = "게시글 목록 조회 성공 (P203 - 게시글 목록 조회 완료)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(
                    value = """
                        {
                          "success": true,
                          "code": "P203",
                          "message": "게시글 목록 조회 완료",
                          "data": {
                            "content": [
                              {
                                "id": 1,
                                "authorId": 1,
                                "title": "첫 번째 게시글",
                                "content": "첫 번째 내용",
                                "type": "GENERAL",
                                "status": "PENDING",
                                "priority": 1
                              }
                            ],
                            "pageable": {
                              "pageNumber": 0,
                              "pageSize": 10
                            },
                            "totalElements": 1,
                            "totalPages": 1
                          }
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<ApiResponse<Page<PostReadResponse>>> getPostsByPaging(
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기", example = "10")
        @RequestParam(defaultValue = "10") int size
    );

    @Operation(
        summary = "게시글 수정",
        description = "기존 게시글을 수정합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "게시글 수정 요청",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PostUpdateApiRequest.class),
                examples = @ExampleObject(
                    name = "게시글 수정 예시",
                    value = """
                        {
                          "requesterId": 1,
                          "title": "수정된 게시글 제목",
                          "content": "수정된 게시글 내용입니다.",
                          "type": "NOTICE",
                          "status": "PENDING",
                          "priority": 2
                        }
                        """
                )
            )
        )
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "200",
            description = "게시글 수정 성공 (P204 - 게시글 수정 완료)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "success": true,
                          "code": "P204",
                          "message": "게시글 수정 완료",
                          "data": null
                        }
                        """
                )
            )
        ),
        @SwaggerApiResponse(
            responseCode = "403",
            description = "게시글 수정 권한 없음 (P002 - 게시글 작성자가 아닙니다)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "success": false,
                          "code": "P002",
                          "message": "게시글 작성자가 아닙니다",
                          "data": null
                        }
                        """
                )
            )
        ),
        @SwaggerApiResponse(
            responseCode = "404",
            description = "게시글을 찾을 수 없음 (P001 - 게시글을 찾을 수 없습니다)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "success": false,
                          "code": "P001",
                          "message": "게시글을 찾을 수 없습니다",
                          "data": null
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<ApiResponse<Void>> updatePost(
        @Parameter(description = "수정할 게시글 ID", required = true, example = "1")
        @PathVariable Long postId,
        @Valid @RequestBody PostUpdateApiRequest request
    );

    @Operation(
        summary = "게시글 삭제",
        description = "게시글을 소프트 삭제합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "게시글 삭제 요청",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PostDeleteApiRequest.class),
                examples = @ExampleObject(
                    name = "게시글 삭제 예시",
                    value = """
                        {
                          "requesterId": 1
                        }
                        """
                )
            )
        )
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "200",
            description = "게시글 삭제 성공 (P205 - 게시글 삭제 완료)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "success": true,
                          "code": "P205",
                          "message": "게시글 삭제 완료",
                          "data": null
                        }
                        """
                )
            )
        ),
        @SwaggerApiResponse(
            responseCode = "403",
            description = "게시글 삭제 권한 없음 (P002 - 게시글 작성자가 아닙니다)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "success": false,
                          "code": "P002",
                          "message": "게시글 작성자가 아닙니다",
                          "data": null
                        }
                        """
                )
            )
        ),
        @SwaggerApiResponse(
            responseCode = "404",
            description = "게시글을 찾을 수 없음 (P001 - 게시글을 찾을 수 없습니다)",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "success": false,
                          "code": "P001",
                          "message": "게시글을 찾을 수 없습니다",
                          "data": null
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<ApiResponse<Void>> deletePost(
        @Parameter(description = "삭제할 게시글 ID", required = true, example = "1")
        @PathVariable Long postId,
        @RequestBody PostDeleteApiRequest request
    );

    @Operation(
        summary = "게시글 검색",
        description = "키워드를 통해 게시글을 검색합니다."
    )
    @ApiResponses(value = {
        @SwaggerApiResponse(
            responseCode = "200",
            description = "게시글 검색 성공 (P203 - 게시글 목록 조회 완료)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(
                    value = """
                        {
                          "success": true,
                          "code": "P203",
                          "message": "게시글 목록 조회 완료",
                          "data": {
                            "content": [
                              {
                                "id": 1,
                                "authorId": 1,
                                "title": "검색된 게시글",
                                "content": "키워드가 포함된 내용",
                                "type": "GENERAL",
                                "status": "PENDING",
                                "priority": 1
                              }
                            ],
                            "pageable": {
                              "pageNumber": 0,
                              "pageSize": 10
                            },
                            "totalElements": 1,
                            "totalPages": 1
                          }
                        }
                        """
                )
            )
        )
    })
    ResponseEntity<ApiResponse<Page<PostReadResponse>>> searchPosts(
        @Parameter(description = "검색 키워드", example = "Spring")
        @RequestParam(required = false) String keyword,
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기", example = "10")
        @RequestParam(defaultValue = "10") int size
    );
}