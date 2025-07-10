package com.back2basics.domain.checklist.controller;

import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_REQUEST_CREATE_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_REQUEST_READ_ALL_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_REQUEST_UPDATE_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_RESPONSE_READ_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.ChecklistResponseCode.CHECKLIST_RESPONSE_STATUS_READ_SUCCESS;

import com.back2basics.checklist.port.in.CreateChecklistUseCase;
import com.back2basics.checklist.port.in.DeleteApprovalUseCase;
import com.back2basics.checklist.port.in.DeleteChecklistUseCase;
import com.back2basics.checklist.port.in.ReadApprovalUseCase;
import com.back2basics.checklist.port.in.ReadChecklistUseCase;
import com.back2basics.checklist.port.in.UpdateApprovalUseCase;
import com.back2basics.checklist.port.in.UpdateChecklistUseCase;
import com.back2basics.checklist.service.result.ApprovalResult;
import com.back2basics.checklist.service.result.ChecklistDetailResult;
import com.back2basics.checklist.service.result.ChecklistInfoResult;
import com.back2basics.domain.checklist.dto.request.CreateChecklistRequest;
import com.back2basics.domain.checklist.dto.request.UpdateApprovalRequest;
import com.back2basics.domain.checklist.dto.request.UpdateChecklistApprovalRequest;
import com.back2basics.domain.checklist.dto.request.UpdateChecklistRequest;
import com.back2basics.domain.checklist.dto.response.ApprovalResponse;
import com.back2basics.domain.checklist.dto.response.ChecklistDetailResponse;
import com.back2basics.domain.checklist.dto.response.ChecklistInfoResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import java.util.List;
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
@RequestMapping("/api/checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final CreateChecklistUseCase createChecklistUseCase;
    private final UpdateChecklistUseCase updateChecklistUseCase;
    private final UpdateApprovalUseCase updateApprovalUseCase;
    private final ReadApprovalUseCase readApprovalUseCase;
    private final ReadChecklistUseCase readChecklistUseCase;
    private final DeleteChecklistUseCase deleteChecklistUseCase;
    private final DeleteApprovalUseCase deleteApprovalUseCase;

    // 체크리스트 생성
    @PostMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> create(@PathVariable Long stepId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody CreateChecklistRequest request) {
        createChecklistUseCase.create(stepId, userDetails.getId(),
            request.toCommand());
        return ApiResponse.success(CHECKLIST_REQUEST_CREATE_SUCCESS);
    }

    // 체크리스트 수정
    @PutMapping("/{checklistId}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long checklistId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody UpdateChecklistRequest request) {
        updateChecklistUseCase.update(checklistId, userDetails.getId(),
            request.toCommand());
        return ApiResponse.success(CHECKLIST_REQUEST_UPDATE_SUCCESS);
    }

    // 답변 수정
    @PutMapping("/approvals/{approvalId}")
    public ResponseEntity<ApiResponse<Void>> updateApproval(@PathVariable Long approvalId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody UpdateApprovalRequest request) {
        updateApprovalUseCase.update(approvalId, userDetails.getId(), request.toCommand());
        return ApiResponse.success(CHECKLIST_REQUEST_UPDATE_SUCCESS);
    }

    // 체크리스트 승인자 추가
    @PutMapping("/approvals/add/{checklistId}")
    public ResponseEntity<ApiResponse<Void>> addApprover(@PathVariable Long checklistId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody UpdateChecklistApprovalRequest request) {
        updateChecklistUseCase.addApproval(checklistId, userDetails.getId(),
            request.toCommand());
        return ApiResponse.success(CHECKLIST_REQUEST_UPDATE_SUCCESS);
    }

    // 전체 체크리스트 조회
    @GetMapping("/{stepId}")
    public ResponseEntity<ApiResponse<List<ChecklistInfoResponse>>> getRequests(
        @PathVariable Long stepId) {
        List<ChecklistInfoResult> results = readChecklistUseCase.findAllByStepId(stepId);
        return ApiResponse.success(CHECKLIST_REQUEST_READ_ALL_SUCCESS,
            ChecklistInfoResponse.from(results));
    }

    // 단건 조회 시 승인 정보 포함
    @GetMapping("/{checklistId}/info")
    public ResponseEntity<ApiResponse<ChecklistDetailResponse>> getApprovalsByChecklistId(
        @PathVariable Long checklistId) {
        ChecklistDetailResult result = readApprovalUseCase.findAllByChecklistId(
            checklistId);
        return ApiResponse.success(CHECKLIST_RESPONSE_READ_SUCCESS,
            ChecklistDetailResponse.from(result));
    }

    // 체크리스트 삭제
    @DeleteMapping("/{checklistId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long checklistId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        deleteChecklistUseCase.delete(userDetails.getId(), checklistId);
        return ApiResponse.success(CHECKLIST_REQUEST_UPDATE_SUCCESS);
    }

    // 답변 삭제
    @DeleteMapping("/approvals/{approvalId}")
    public ResponseEntity<ApiResponse<Void>> deleteApproval(@PathVariable Long approvalId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        deleteApprovalUseCase.delete(userDetails.getId(), approvalId);
        return ApiResponse.success(CHECKLIST_REQUEST_UPDATE_SUCCESS);
    }

    // 필요없어보임
//    @GetMapping("/{checklistId}")
//    public ResponseEntity<ApiResponse<ApprovalInfoResponse>> getRequestDetail(
//        @PathVariable Long checklistId) {
//        ChecklistInfoResult result = readChecklistUseCase.findByChecklistId(checklistId);
//        return ApiResponse.success(CHECKLIST_REQUEST_READ_SUCCESS,
//            ApprovalInfoResponse.from(result));
//    }

    // 필요없어보임
    @GetMapping("/response/{approvalId}")
    public ResponseEntity<ApiResponse<ApprovalResponse>> getResponse(
        @PathVariable Long approvalId) {
        ApprovalResult result = readApprovalUseCase.findById(approvalId);
        return ApiResponse.success(CHECKLIST_RESPONSE_STATUS_READ_SUCCESS,
            ApprovalResponse.from(result));
    }
}
