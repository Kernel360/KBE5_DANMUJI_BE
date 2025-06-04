package com.back2basics.domain.answer.swagger;

import com.back2basics.domain.answer.dto.request.AnswerCreateRequest;
import com.back2basics.domain.answer.dto.request.AnswerUpdateRequest;
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

@Tag(name = "Answer", description = "답변 관리 API")
public interface AnswerApiDocs {

    @Operation(summary = "답변 생성", description = "새로운 답변을 생성합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AnswerCreateRequest.class),
                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_CREATE_REQUEST))
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201", description = "답변 생성 성공 (AN201)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_CREATE_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Long>> createAnswer(
        @RequestBody @Valid AnswerCreateRequest request);

    @Operation(summary = "답변 수정", description = "기존 답변을 수정합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AnswerUpdateRequest.class),
                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_UPDATE_REQUEST))
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201", description = "답변 수정 성공 (AN203)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_UPDATE_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Void>> updateAnswer(
        @Parameter(description = "답변 ID", example = "1") @PathVariable Long answerId,
        @RequestBody @Valid AnswerUpdateRequest request);

    @Operation(summary = "답변 삭제", description = "답변을 삭제합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AnswerDeleteRequest.class),
                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_DELETE_REQUEST))
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "답변 삭제 성공 (AN202)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_DELETE_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Void>> deleteAnswer(
        @Parameter(description = "답변 ID", example = "1") @PathVariable Long answerId,
        @RequestBody @Valid AnswerDeleteRequest request);

}
