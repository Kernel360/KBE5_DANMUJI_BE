package com.back2basics.domain.question.controller;

import com.back2basics.domain.question.dto.request.QuestionCreateRequest;
import com.back2basics.domain.question.dto.request.QuestionUpdateRequest;
import com.back2basics.domain.question.dto.response.QuestionResponse;
import com.back2basics.domain.question.swagger.QuestionApiDocs;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.question.port.in.QuestionCreateUseCase;
import com.back2basics.question.port.in.QuestionDeleteUseCase;
import com.back2basics.question.port.in.QuestionReadUseCase;
import com.back2basics.question.port.in.QuestionStatusUpdateUseCase;
import com.back2basics.question.port.in.QuestionUpdateUseCase;
import com.back2basics.question.service.result.QuestionResult;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController implements QuestionApiDocs {

    private final QuestionReadUseCase questionReadUseCase;
    private final QuestionCreateUseCase questionCreateUseCase;
    private final QuestionUpdateUseCase questionUpdateUseCase;
    private final QuestionDeleteUseCase questionDeleteUseCase;
    private final QuestionStatusUpdateUseCase statusUpdateUseCase;

    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponse>> getQuestionById(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long questionId
    ) {
        QuestionResult result = questionReadUseCase.getQuestionById(customUserDetails.getId(),
            questionId);
        QuestionResponse response = QuestionResponse.toResponse(result);
        return ApiResponse.success(QuestionResponseCode.QUESTION_READ_SUCCESS, response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<QuestionResponse>>> getAllQuestions(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionResult> results = questionReadUseCase.getAllQuestions(
            customUserDetails.getId(), pageable);
        Page<QuestionResponse> responses = results.map(QuestionResponse::toResponse);
        return ApiResponse.success(QuestionResponseCode.QUESTION_READ_SUCCESS, responses);
    }

    @GetMapping("/post/{postId}") // 특정 게시글에 대한 질문 조회용 api
    public ResponseEntity<ApiResponse<Page<QuestionResponse>>> getQuestionsByPostId(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long postId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionResult> resultPage = questionReadUseCase.getQuestionsByPostId(
            customUserDetails.getId(), postId,
            pageable);
        Page<QuestionResponse> responsePage = resultPage.map(QuestionResponse::toResponse);
        return ApiResponse.success(QuestionResponseCode.QUESTION_READ_SUCCESS, responsePage);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createQuestion(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody @Valid QuestionCreateRequest request) {
        Long questionId = questionCreateUseCase.create(customUserDetails.getId(),
            request.toCommand());
        return ApiResponse.success(QuestionResponseCode.QUESTION_CREATE_SUCCESS, questionId);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<ApiResponse<Void>> updateQuestion(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long questionId,
        @RequestBody @Valid QuestionUpdateRequest request) {
        questionUpdateUseCase.update(customUserDetails.getId(), questionId, request.toCommand());
        return ApiResponse.success(QuestionResponseCode.QUESTION_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long questionId) {
        questionDeleteUseCase.delete(customUserDetails.getId(), questionId);
        return ApiResponse.success(QuestionResponseCode.QUESTION_DELETE_SUCCESS);
    }

    @PatchMapping("/{questionId}/answered")
    public ResponseEntity<ApiResponse<Void>> markAsAnswered(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long questionId) {
        statusUpdateUseCase.markAsAnswered(customUserDetails.getId(), questionId);
        return ApiResponse.success(QuestionResponseCode.QUESTION_MARK_AS_ANSWERED);
    }

    @PatchMapping("/{questionId}/resolved")
    public ResponseEntity<ApiResponse<Void>> markAsResolved(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long questionId
    ) {
        statusUpdateUseCase.markAsResolved(customUserDetails.getId(), questionId);
        return ApiResponse.success(QuestionResponseCode.QUESTION_MARK_AS_RESOLVED);
    }
}