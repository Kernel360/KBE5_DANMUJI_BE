//package com.back2basics.domain.answer.swagger;
//
//import com.back2basics.domain.answer.dto.request.AnswerCreateRequest;
//import com.back2basics.domain.answer.dto.request.AnswerUpdateRequest;
//import com.back2basics.domain.answer.dto.response.AnswerReadResponse;
//import com.back2basics.global.response.result.ApiResponse;
//import com.back2basics.security.model.CustomUserDetails;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.ExampleObject;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import java.util.List;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@Tag(name = "Answer", description = "답변 관리 API")
//public interface AnswerApiDocs {
//
//    @Operation(summary = "답변 생성", description = "새로운 답변을 생성합니다.",
//        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//            required = true,
//            content = @Content(mediaType = "application/json",
//                schema = @Schema(implementation = AnswerCreateRequest.class),
//                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_CREATE_REQUEST))
//        )
//    )
//    @ApiResponses(value = {
//        @io.swagger.v3.oas.annotations.responses.ApiResponse(
//            responseCode = "201", description = "답변 생성 성공 (AN201)",
//            content = @Content(mediaType = "application/json",
//                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_CREATE_SUCCESS))
//        )
//    })
//    ResponseEntity<ApiResponse<Long>> createAnswer(
//        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
//        @RequestBody @Valid AnswerCreateRequest request);
//
//    @Operation(summary = "답변 수정", description = "기존 답변을 수정합니다.",
//        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//            content = @Content(mediaType = "application/json",
//                schema = @Schema(implementation = AnswerUpdateRequest.class),
//                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_UPDATE_REQUEST))
//        )
//    )
//    @ApiResponses(value = {
//        @io.swagger.v3.oas.annotations.responses.ApiResponse(
//            responseCode = "201", description = "답변 수정 성공 (AN203)",
//            content = @Content(mediaType = "application/json",
//                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_UPDATE_SUCCESS))
//        )
//    })
//    ResponseEntity<ApiResponse<Void>> updateAnswer(
//        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
//        @Parameter(description = "답변 ID", example = "1") @PathVariable Long answerId,
//        @RequestBody @Valid AnswerUpdateRequest request);
//
//    @Operation(summary = "답변 삭제", description = "답변을 삭제합니다.")
//    @ApiResponses(value = {
//        @io.swagger.v3.oas.annotations.responses.ApiResponse(
//            responseCode = "200", description = "답변 삭제 성공 (AN202)",
//            content = @Content(mediaType = "application/json",
//                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_DELETE_SUCCESS))
//        )
//    })
//    ResponseEntity<ApiResponse<Void>> deleteAnswer(
//        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
//        @Parameter(description = "답변 ID", example = "1") @PathVariable Long answerId);
//
//    @Operation(summary = "질문에 대한 답변 조회", description = "질문 ID에 해당하는 모든 답변을 조회합니다.")
//    @ApiResponses(value = {
//        @io.swagger.v3.oas.annotations.responses.ApiResponse(
//            responseCode = "200", description = "답변 조회 완료 (AN204)",
//            content = @Content(mediaType = "application/json",
//                examples = @ExampleObject(value = AnswerDocsResult.ANSWER_READ_SUCCESS))
//        )
//    })
//    ResponseEntity<ApiResponse<List<AnswerReadResponse>>> getAnswersByQuestionId(
//        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
//        @Parameter(description = "질문 ID", example = "1") @PathVariable Long questionId);
//
//}
