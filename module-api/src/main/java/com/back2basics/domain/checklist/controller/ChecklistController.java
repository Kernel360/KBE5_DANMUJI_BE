package com.back2basics.domain.checklist.controller;

import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_REQUEST_CREATE_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_REQUEST_READ_ALL_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_REQUEST_READ_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_REQUEST_UPDATE_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_RESPONSE_READ_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_RESPONSE_STATUS_READ_SUCCESS;

import com.back2basics.checklist.port.in.CreateChecklistUseCase;
import com.back2basics.checklist.port.in.ReadApprovalUseCase;
import com.back2basics.checklist.port.in.UpdateApprovalUseCase;
import com.back2basics.checklist.service.result.ApprovalResult;
import com.back2basics.checklist.service.result.ChecklistInfoResult;
import com.back2basics.domain.checklist.dto.request.CreateChecklistRequest;
import com.back2basics.domain.checklist.dto.request.UpdateApprovalRequest;
import com.back2basics.domain.checklist.dto.request.UpdateChecklistApprovalRequest;
import com.back2basics.domain.checklist.dto.response.ApprovalInfoResponse;
import com.back2basics.domain.checklist.dto.response.ApproverResponse;
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
@RequestMapping("/api/checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final CreateChecklistUseCase createChecklistUseCase;
    private final UpdateApprovalUseCase updateApprovalUseCase;
    private final ReadApprovalUseCase readApprovalUseCase;

    // 체크리스트 생성
    @PostMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> create(@PathVariable Long stepId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody CreateChecklistRequest request) {
        createChecklistUseCase.create(stepId, userDetails.getId(),
            request.toCommand());
        return ApiResponse.success(CHECKLIST_REQUEST_CREATE_SUCCESS);
    }

    // 체크리스트 승인자 추가
    @PutMapping("/approval/add/{checklistId}")
    public ResponseEntity<ApiResponse<Void>> addApprover(@PathVariable Long checklistId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody UpdateChecklistApprovalRequest request) {
        updateApprovalUseCase.addApproval(checklistId, userDetails.getId(),
            request.toCommand());
        return ApiResponse.success(CHECKLIST_REQUEST_UPDATE_SUCCESS);
    }

    // 체크리스트 상태 변경
    @PutMapping("/{checklistId}/status")
    public ResponseEntity<ApiResponse<Void>> change(@PathVariable Long checklistId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody UpdateApprovalRequest request) {
        updateApprovalUseCase.change(checklistId, userDetails.getId(), request.toCommand());
        return ApiResponse.success(CHECKLIST_REQUEST_UPDATE_SUCCESS);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ApiResponse<ApprovalInfoResponse>> getRequestDetail(
        @PathVariable Long requestId) {
        ChecklistInfoResult result = readApprovalUseCase.findByRequestId(requestId);
        return ApiResponse.success(CHECKLIST_REQUEST_READ_SUCCESS,
            ApprovalInfoResponse.from(result));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalInfoResponse>>> getRequests() {
        List<ChecklistInfoResult> results = readApprovalUseCase.findAll();
        return ApiResponse.success(CHECKLIST_REQUEST_READ_ALL_SUCCESS,
            ApprovalInfoResponse.from(results));
    }

    @GetMapping("/{requestId}/info")
    public ResponseEntity<ApiResponse<List<ApproverResponse>>> getResponsesByRequestId(
        @PathVariable Long requestId) {
        List<ApprovalResult> results = readApprovalUseCase.findResponsesByRequestId(requestId);
        return ApiResponse.success(CHECKLIST_RESPONSE_READ_SUCCESS, ApproverResponse.from(results));
    }

    @GetMapping("/response/{responseId}")
    public ResponseEntity<ApiResponse<ApproverResponse>> getResponse(
        @PathVariable Long responseId) {
        ApprovalResult result = readApprovalUseCase.findByResponseId(responseId);
        return ApiResponse.success(CHECKLIST_RESPONSE_STATUS_READ_SUCCESS,
            ApproverResponse.from(result));
    }
}
