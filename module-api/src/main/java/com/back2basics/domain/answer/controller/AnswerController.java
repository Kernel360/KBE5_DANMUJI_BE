package com.back2basics.domain.answer.controller;

import com.back2basics.answer.port.in.CreateAnswerUseCase;
import com.back2basics.answer.port.in.DeleteAnswerUseCase;
import com.back2basics.answer.port.in.ReadAnswerUseCase;
import com.back2basics.answer.port.in.UpdateAnswerUseCase;
import com.back2basics.answer.service.result.ReadAnswerResult;
import com.back2basics.domain.answer.controller.code.AnswerResponseCode;
import com.back2basics.domain.answer.dto.request.CreateAnswerRequest;
import com.back2basics.domain.answer.dto.request.UpdateAnswerRequest;
import com.back2basics.domain.answer.dto.response.ReadAnswerResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class AnswerController {

    private final ReadAnswerUseCase readAnswerUseCase;
    private final CreateAnswerUseCase createAnswerUseCase;
    private final UpdateAnswerUseCase updateAnswerUseCase;
    private final DeleteAnswerUseCase deleteAnswerUseCase;

    @GetMapping("/{inquiryId}")
    public ResponseEntity<ApiResponse<Page<ReadAnswerResponse>>> getAnswersByInquiryId(
        @PathVariable Long inquiryId,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "id",
            direction = Sort.Direction.DESC
        ) Pageable pageable
    ) {

        Page<ReadAnswerResult> results = readAnswerUseCase.getAnswersByInquiryId(
            inquiryId,
            pageable);

        return ApiResponse.success(AnswerResponseCode.ANSWER_READ_SUCCESS,
            results.map(ReadAnswerResponse::toResponse));
    }

    @PostMapping("/{inquiryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Long>> createAnswer(
        @PathVariable Long inquiryId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody @Valid CreateAnswerRequest request) {
        Long createdId = createAnswerUseCase.createAnswer(inquiryId, customUserDetails.getId(),
            request.toCommand());
        return ApiResponse.success(AnswerResponseCode.ANSWER_CREATE_SUCCESS, createdId);
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<ApiResponse<Void>> updateAnswer(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long answerId,
        @Valid @RequestBody UpdateAnswerRequest request
    ) {
        updateAnswerUseCase.updateAnswer(customUserDetails.getId(),
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
