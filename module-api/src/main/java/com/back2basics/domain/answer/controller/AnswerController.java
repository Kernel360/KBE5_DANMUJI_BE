package com.back2basics.domain.answer.controller;

import com.back2basics.answer.port.in.AnswerCreateUseCase;
import com.back2basics.answer.port.in.AnswerDeleteUseCase;
import com.back2basics.answer.port.in.AnswerUpdateUseCase;
import com.back2basics.domain.answer.controller.code.AnswerResponseCode;
import com.back2basics.domain.answer.dto.request.AnswerCreateRequest;
import com.back2basics.domain.answer.dto.request.AnswerDeleteRequest;
import com.back2basics.domain.answer.dto.request.AnswerUpdateRequest;
import com.back2basics.global.response.result.ApiResponse;
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
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerCreateUseCase createAnswerUseCase;
    private final AnswerUpdateUseCase updateAnswerUseCase;
    private final AnswerDeleteUseCase deleteAnswerUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createAnswer(
        @RequestBody @Valid AnswerCreateRequest request) {
        Long createdId = createAnswerUseCase.createAnswer(request.toCommand());
        return ApiResponse.success(AnswerResponseCode.ANSWER_CREATE_SUCCESS, createdId);
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<ApiResponse<Void>> updateAnswer(
        @PathVariable Long answerId,
        @Valid @RequestBody AnswerUpdateRequest request
    ) {
        updateAnswerUseCase.updateAnswer(answerId, request.toCommand());
        return ApiResponse.success(AnswerResponseCode.ANSWER_UPDATE_SUCCESS);
    }

    @DeleteMapping("/delete/{answerId}")
    public ResponseEntity<ApiResponse<Void>> deleteAnswer(
        @PathVariable Long answerId,
        @Valid @RequestBody AnswerDeleteRequest request
    ) {
        Long requesterId = request.getRequesterId();
        deleteAnswerUseCase.deletAnswer(answerId, requesterId);
        return ApiResponse.success(AnswerResponseCode.ANSWER_DELETE_SUCCESS);
    }

}
