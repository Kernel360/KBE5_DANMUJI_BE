package com.back2basics.domain.approval.controller;

import static com.back2basics.domain.approval.controller.code.ApprovalResponseCode.APPROVAL_REQUEST_CREATE_SUCCESS;
import static com.back2basics.domain.approval.controller.code.ApprovalResponseCode.APPROVAL_REQUEST_READ_ALL_SUCCESS;
import static com.back2basics.domain.approval.controller.code.ApprovalResponseCode.APPROVAL_REQUEST_READ_SUCCESS;
import static com.back2basics.domain.approval.controller.code.ApprovalResponseCode.APPROVAL_REQUEST_UPDATE_SUCCESS;
import static com.back2basics.domain.approval.controller.code.ApprovalResponseCode.APPROVAL_RESPONSE_READ_SUCCESS;
import static com.back2basics.domain.approval.controller.code.ApprovalResponseCode.APPROVAL_RESPONSE_STATUS_READ_SUCCESS;

import com.back2basics.approval.port.in.CreateApprovalRequestUseCase;
import com.back2basics.approval.port.in.ReadApprovalUseCase;
import com.back2basics.approval.port.in.UpdateApprovalResponseUseCase;
import com.back2basics.approval.service.result.ApprovalInfoResult;
import com.back2basics.approval.service.result.ApproverResult;
import com.back2basics.domain.approval.dto.request.CreateApprovalRequest;
import com.back2basics.domain.approval.dto.request.UpdateApprovalRequest;
import com.back2basics.domain.approval.dto.response.ApprovalInfoResponse;
import com.back2basics.domain.approval.dto.response.ApproverResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project-steps/approval")
@RequiredArgsConstructor
public class ApprovalController {

    private final CreateApprovalRequestUseCase createApprovalRequestUseCase;
    private final UpdateApprovalResponseUseCase updateApprovalResponseUseCase;
    private final ReadApprovalUseCase readApprovalUseCase;

    // 승인 요청 생성
    @PostMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> create(@PathVariable Long stepId,
        @AuthenticationPrincipal
        CustomUserDetails userDetails, @RequestBody CreateApprovalRequest request) {

        createApprovalRequestUseCase.create(stepId, userDetails.getId(),
            request.toCommand());
        return ApiResponse.success(APPROVAL_REQUEST_CREATE_SUCCESS);
    }

    // 승인자 추가
    @PutMapping("/add-responses/{requestId}")
    public ResponseEntity<ApiResponse<Void>> addApprover(@PathVariable Long requestId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody CreateApprovalRequest request) {
        updateApprovalResponseUseCase.addApprover(requestId, userDetails.getId(),
            request.toCommand());
        return ApiResponse.success(APPROVAL_REQUEST_UPDATE_SUCCESS);
    }

    // 상태 변경
    @PutMapping("/{responseId}/status")
    public ResponseEntity<ApiResponse<Void>> change(@PathVariable Long responseId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody UpdateApprovalRequest request) {
        updateApprovalResponseUseCase.change(responseId, userDetails.getId(), request.toCommand());
        return ApiResponse.success(APPROVAL_REQUEST_UPDATE_SUCCESS);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ApiResponse<ApprovalInfoResponse>> getRequestDetail(
        @PathVariable Long requestId) {
        ApprovalInfoResult result = readApprovalUseCase.findByRequestId(requestId);
        return ApiResponse.success(APPROVAL_REQUEST_READ_SUCCESS,
            ApprovalInfoResponse.from(result));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalInfoResponse>>> getRequests() {
        List<ApprovalInfoResult> results = readApprovalUseCase.findAll();
        return ApiResponse.success(APPROVAL_REQUEST_READ_ALL_SUCCESS,
            ApprovalInfoResponse.from(results));
    }

    @GetMapping("/{requestId}/info")
    public ResponseEntity<ApiResponse<List<ApproverResponse>>> getResponsesByRequestId(
        @PathVariable Long requestId) {
        List<ApproverResult> results = readApprovalUseCase.findResponsesByRequestId(requestId);
        return ApiResponse.success(APPROVAL_RESPONSE_READ_SUCCESS, ApproverResponse.from(results));
    }

    @GetMapping("/response/{responseId}")
    public ResponseEntity<ApiResponse<ApproverResponse>> getResponse(
        @PathVariable Long responseId) {
        ApproverResult result = readApprovalUseCase.findByResponseId(responseId);
        return ApiResponse.success(APPROVAL_RESPONSE_STATUS_READ_SUCCESS,
            ApproverResponse.from(result));
    }
}
