package com.back2basics.domain.comment.swagger;

import com.back2basics.domain.comment.dto.request.CommentCreateRequest;
import com.back2basics.domain.comment.dto.request.CommentUpdateRequest;
import com.back2basics.global.response.result.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Comment", description = "댓글 관리 API")
public interface CommentApiDocs {

    @Operation(summary = "댓글 생성", description = "새로운 댓글을 생성합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "댓글 생성 요청",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CommentCreateRequest.class),
                examples = @ExampleObject(name = "댓글 생성 예시", value = CommentDocsResult.COMMENT_CREATE_REQUEST)
            )
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "댓글 생성 성공 (C201 - 댓글 생성 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(name = "성공 응답", value = CommentDocsResult.COMMENT_CREATE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터 (C002 - Invalid input type)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CommentDocsResult.INVALID_INPUT))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "게시글을 찾을 수 없음 (P001 - 게시글을 찾을 수 없습니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CommentDocsResult.POST_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Long>> createComment(
        @RequestBody @Valid CommentCreateRequest request);

    @Operation(summary = "댓글 수정", description = "기존 댓글을 수정합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "댓글 수정 요청",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CommentUpdateRequest.class),
                examples = @ExampleObject(name = "댓글 수정 예시", value = CommentDocsResult.COMMENT_UPDATE_REQUEST))
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "댓글 수정 성공 (C203 - 댓글 수정 완료)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CommentDocsResult.COMMENT_UPDATE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "댓글 수정 권한 없음 (C003 - 댓글 작성자가 아닙니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CommentDocsResult.INVALID_COMMENT_AUTHOR))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "댓글을 찾을 수 없음 (C001 - 댓글을 찾을 수 없습니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CommentDocsResult.COMMENT_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Void>> updateComment(
        @Parameter(description = "수정할 댓글 ID", required = true, example = "1") @PathVariable Long commentId,
        @Valid @RequestBody CommentUpdateRequest request);

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "댓글 삭제 요청",
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CommentDeleteRequest.class),
                examples = @ExampleObject(name = "댓글 삭제 예시", value = CommentDocsResult.COMMENT_DELETE_REQUEST))
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "댓글 삭제 성공 (C204 - 댓글 삭제 완료)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CommentDocsResult.COMMENT_DELETE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "댓글 삭제 권한 없음 (C003 - 댓글 작성자가 아닙니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CommentDocsResult.INVALID_COMMENT_AUTHOR))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "댓글을 찾을 수 없음 (C001 - 댓글을 찾을 수 없습니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CommentDocsResult.COMMENT_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Void>> deleteComment(
        @Parameter(description = "삭제할 댓글 ID", required = true, example = "1") @PathVariable Long commentId,
        @RequestBody CommentDeleteRequest request);
}