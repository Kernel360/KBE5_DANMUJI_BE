package com.back2basics.domain.question.controller;

import com.back2basics.domain.question.dto.request.QuestionCreateRequest;
import com.back2basics.domain.question.dto.request.QuestionDeleteRequest;
import com.back2basics.domain.question.dto.request.QuestionUpdateRequest;
import com.back2basics.domain.question.swagger.QuestionApiDocs;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.question.port.in.QuestionCreateUseCase;
import com.back2basics.question.port.in.QuestionDeleteUseCase;
import com.back2basics.question.port.in.QuestionReadUseCase;
import com.back2basics.question.port.in.QuestionStatusUpdateUseCase;
import com.back2basics.question.port.in.QuestionUpdateUseCase;
import com.back2basics.question.service.result.QuestionResult;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResult>>> getAllQuestions() {
        List<QuestionResult> results = questionReadUseCase.getAllQuestions();
        return ApiResponse.success(QuestionResponseCode.QUESTION_READ_SUCCESS, results);
    }

    @GetMapping("/post/{postId}") // 특정 게시글에 대한 질문 조회용 api
    public ResponseEntity<ApiResponse<List<QuestionResult>>> getQuestionsByPostId(
        @PathVariable Long postId
    ) {
        List<QuestionResult> results = questionReadUseCase.getQuestionsByPostId(postId);
        return ApiResponse.success(QuestionResponseCode.QUESTION_READ_SUCCESS, results);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createQuestion(
        @RequestBody @Valid QuestionCreateRequest request) {
        Long questionId = questionCreateUseCase.create(request.toCommand());
        return ApiResponse.success(QuestionResponseCode.QUESTION_CREATE_SUCCESS, questionId);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<ApiResponse<Void>> updateQuestion(
        @PathVariable Long questionId,
        @RequestBody @Valid QuestionUpdateRequest request) {
        questionUpdateUseCase.update(questionId, request.toCommand());
        return ApiResponse.success(QuestionResponseCode.QUESTION_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(
        @PathVariable Long questionId,
        @RequestBody @Valid QuestionDeleteRequest request) {
        questionDeleteUseCase.delete(questionId, request.getRequesterId());
        return ApiResponse.success(QuestionResponseCode.QUESTION_DELETE_SUCCESS);
    }

    @PatchMapping("/{questionId}/answered")
    public ResponseEntity<ApiResponse<Void>> markAsAnswered(@PathVariable Long questionId) {
        statusUpdateUseCase.markAsAnswered(questionId);
        return ApiResponse.success(QuestionResponseCode.QUESTION_MARK_AS_ANSWERED);
    }

    @PatchMapping("/{questionId}/resolved")
    public ResponseEntity<ApiResponse<Void>> markAsResolved(
        @PathVariable Long questionId,
        @RequestParam Long requesterId
    ) {
        statusUpdateUseCase.markAsResolved(questionId, requesterId);
        return ApiResponse.success(QuestionResponseCode.QUESTION_MARK_AS_RESOLVED);
    }
}