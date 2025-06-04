package com.back2basics.domain.question.swagger;

import com.back2basics.domain.question.dto.request.QuestionCreateRequest;
import com.back2basics.domain.question.dto.request.QuestionUpdateRequest;
import com.back2basics.domain.question.dto.response.QuestionResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Question", description = "질문 관리 API")
public interface QuestionApiDocs {

    @Operation(summary = "질문 단건 조회", description = "질문 ID로 질문을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "질문 조회 성공 (Q002)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_READ_ONE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = "질문 없음 (Q001)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<QuestionResponse>> getQuestionById(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Parameter(description = "질문 ID", example = "1") @PathVariable Long questionId
    );

    @Operation(summary = "전체 질문 목록 조회", description = "전체 질문 목록을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "질문 목록 조회 성공 (Q002)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_LIST_READ_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Page<QuestionResponse>>> getAllQuestions(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "게시글 질문 목록 조회", description = "게시글 ID로 질문 목록을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "질문 목록 조회 성공 (Q002)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_READ_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Page<QuestionResponse>>> getQuestionsByPostId(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Parameter(description = "게시글 ID", example = "1") @PathVariable Long postId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "질문 생성", description = "새로운 질문을 생성합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = QuestionCreateRequest.class),
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_CREATE_REQUEST))
        ))
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201", description = "질문 생성 성공 (Q001)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_CREATE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", description = "잘못된 요청 (C002)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.INVALID_INPUT))
        )
    })
    ResponseEntity<ApiResponse<Long>> createQuestion(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody @Valid QuestionCreateRequest request);

    @Operation(summary = "질문 수정", description = "질문 내용을 수정합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = QuestionUpdateRequest.class),
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_UPDATE_REQUEST))
        ))
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "질문 수정 성공 (Q003)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_UPDATE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403", description = "작성자 아님 (Q002)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.INVALID_QUESTION_AUTHOR))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = "질문 없음 (Q001)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Void>> updateQuestion(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Parameter(description = "질문 ID", example = "1") @PathVariable Long questionId,
        @RequestBody @Valid QuestionUpdateRequest request);

    @Operation(summary = "질문 삭제", description = "질문을 삭제합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "질문 삭제 성공 (Q004)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_DELETE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403", description = "작성자 아님 (Q002)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.INVALID_QUESTION_AUTHOR))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", description = "질문 없음 (Q001)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Void>> deleteQuestion(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Parameter(description = "질문 ID", example = "1") @PathVariable Long questionId);

    @Operation(summary = "답변 완료 상태 표시", description = "질문 상태를 답변 완료로 변경합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "답변 상태 변경 성공 (Q005)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_MARK_ANSWERED_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Void>> markAsAnswered(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Parameter(description = "질문 ID", example = "1") @PathVariable Long questionId);

    @Operation(summary = "해결됨 상태 표시", description = "질문 상태를 해결됨으로 변경합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "해결 상태 변경 성공 (Q006)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.QUESTION_MARK_RESOLVED_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403", description = "작성자 아님 (Q002)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = QuestionDocsResult.INVALID_QUESTION_AUTHOR))
        )
    })
    ResponseEntity<ApiResponse<Void>> markAsResolved(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Parameter(description = "질문 ID", example = "1") @PathVariable Long questionId);
}