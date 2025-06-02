package com.back2basics.domain.question.controller;

import com.back2basics.domain.question.dto.request.QuestionCreateRequest;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.question.port.in.QuestionCreateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionCreateUseCase questionCreateUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createQuestion(
        @RequestBody @Valid QuestionCreateRequest request) {
        Long questionId = questionCreateUseCase.create(request.toCommand());
        return ApiResponse.success(QuestionResponseCode.QUESTION_CREATE_SUCCESS, questionId);
    }
}