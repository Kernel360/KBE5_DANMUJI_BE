package com.back2basics.domain.approval.controller;

import static com.back2basics.domain.approval.controller.code.ApprovalResponseCode.APPROVAL_REQUEST_CREATE_SUCCESS;
import static com.back2basics.domain.approval.controller.code.ApprovalResponseCode.APPROVAL_REQUEST_UPDATE_SUCCESS;

import com.back2basics.approval.port.in.CreateApprovalRequestUseCase;
import com.back2basics.approval.port.in.UpdateApprovalResponseUseCase;
import com.back2basics.domain.approval.dto.request.CreateApprovalRequest;
import com.back2basics.domain.approval.dto.request.UpdateApprovalRequest;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
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

    // 생성
    @PostMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> create(@PathVariable Long stepId,
        @AuthenticationPrincipal
        CustomUserDetails userDetails, @RequestBody CreateApprovalRequest request) {

        createApprovalRequestUseCase.create(stepId, userDetails.getId(),
            request.toCommand());
        return ApiResponse.success(APPROVAL_REQUEST_CREATE_SUCCESS);
    }

    // todo:유저 추가


    // 상태 변경
    @PutMapping("/{responseId}/status")
    public ResponseEntity<ApiResponse<Void>> change(@PathVariable Long responseId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody UpdateApprovalRequest request) {
        updateApprovalResponseUseCase.change(responseId, userDetails.getId(), request.toCommand());
        return ApiResponse.success(APPROVAL_REQUEST_UPDATE_SUCCESS);
    }

    @GetMapping("/{projectId}/project")
    public ResponseEntity<ApiResponse> getApprovalStatusesByProjectId(
        @PathVariable Long projectId) {

    }
}
