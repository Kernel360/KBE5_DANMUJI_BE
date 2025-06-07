package com.back2basics.domain.answer.controller;

import com.back2basics.answer.port.in.AnswerCreateUseCase;
import com.back2basics.answer.port.in.AnswerDeleteUseCase;
import com.back2basics.answer.port.in.AnswerReadUseCase;
import com.back2basics.answer.port.in.AnswerUpdateUseCase;
import com.back2basics.answer.service.result.AnswerReadResult;
import com.back2basics.domain.answer.controller.code.AnswerResponseCode;
import com.back2basics.domain.answer.dto.request.AnswerCreateRequest;
import com.back2basics.domain.answer.dto.request.AnswerUpdateRequest;
import com.back2basics.domain.answer.dto.response.AnswerReadResponse;
import com.back2basics.domain.answer.swagger.AnswerApiDocs;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController implements AnswerApiDocs {

    private final AnswerReadUseCase answerReadUseCase;
    private final AnswerCreateUseCase createAnswerUseCase;
    private final AnswerUpdateUseCase updateAnswerUseCase;
    private final AnswerDeleteUseCase deleteAnswerUseCase;

    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResponse<List<AnswerReadResponse>>> getAnswersByQuestionId(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long questionId
    ) {

        List<AnswerReadResult> resultList = answerReadUseCase.getAnswersByQuestionId(
            customUserDetails.getId(),
            questionId);
        List<AnswerReadResponse> responseList = resultList.stream()
            .map(AnswerReadResponse::toResponse)
            .collect(Collectors.toList());
        return ApiResponse.success(AnswerResponseCode.ANSWER_READ_SUCCESS, responseList);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createAnswer(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody @Valid AnswerCreateRequest request) {
        Long createdId = createAnswerUseCase.createAnswer(customUserDetails.getId(),
            customUserDetails.getIp(),
            request.toCommand());
        return ApiResponse.success(AnswerResponseCode.ANSWER_CREATE_SUCCESS, createdId);
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<ApiResponse<Void>> updateAnswer(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long answerId,
        @Valid @RequestBody AnswerUpdateRequest request
    ) {
        updateAnswerUseCase.updateAnswer(customUserDetails.getId(), customUserDetails.getIp(),
            answerId, request.toCommand());
        return ApiResponse.success(AnswerResponseCode.ANSWER_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<ApiResponse<Void>> deleteAnswer(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long answerId
    ) {
        deleteAnswerUseCase.deleteAnswer(customUserDetails.getId(), answerId);
        return ApiResponse.success(AnswerResponseCode.ANSWER_DELETE_SUCCESS);
    }

}
