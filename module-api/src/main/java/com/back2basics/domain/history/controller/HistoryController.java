package com.back2basics.domain.history.controller;

import com.back2basics.domain.board.controller.code.PostResponseCode;
import com.back2basics.domain.history.dto.response.HistoryDetailResponse;
import com.back2basics.domain.history.dto.response.HistorySimpleResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.history.port.in.HistoryReadUseCase;
import com.back2basics.history.service.result.HistoryDetailResult;
import com.back2basics.history.service.result.HistorySimpleResult;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/histories")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryReadUseCase historyReadUseCase;

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<HistorySimpleResponse>>> getAllHistories(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HistorySimpleResult> resultPage = historyReadUseCase.getAllHistories(
            customUserDetails.getId(), pageable);
        Page<HistorySimpleResponse> responsePage = resultPage.map(
            HistorySimpleResponse::toResponse);

        return ApiResponse.success(PostResponseCode.HISTORY_READ_ALL_SUCCESS, responsePage);
    }

    @GetMapping("/{historyId}")
    public ResponseEntity<ApiResponse<HistoryDetailResponse>> getHistoryById(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long historyId) {
        Long userId = customUserDetails.getId();
        HistoryDetailResult result = historyReadUseCase.getHistoryById(userId, historyId);
        HistoryDetailResponse response = HistoryDetailResponse.toResponse(result);

        return ApiResponse.success(PostResponseCode.HISTORY_READ_SUCCESS, response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<HistorySimpleResponse>>> searchPosts(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Valid @ModelAttribute HistorySearchRequest request,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HistorySimpleResult> resultPage = historyReadUseCase.searchHistories(
            customUserDetails.getId(), request.toCommand(), pageable);
        Page<HistorySimpleResponse> responsePage = resultPage.map(
            HistorySimpleResponse::toResponse);

        return ApiResponse.success(PostResponseCode.HISTORY_SEARCH_SUCCESS, responsePage);
    }

}
