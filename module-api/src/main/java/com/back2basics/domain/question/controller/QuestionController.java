package com.back2basics.domain.question.controller;

import com.back2basics.domain.question.dto.request.QuestionCreateRequest;
import com.back2basics.domain.question.dto.request.QuestionDeleteRequest;
import com.back2basics.domain.question.dto.request.QuestionUpdateRequest;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.question.port.in.QuestionCreateUseCase;
import com.back2basics.question.port.in.QuestionDeleteUseCase;
import com.back2basics.question.port.in.QuestionUpdateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionCreateUseCase questionCreateUseCase;
    private final QuestionUpdateUseCase questionUpdateUseCase;
    private final QuestionDeleteUseCase questionDeleteUseCase;

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
}