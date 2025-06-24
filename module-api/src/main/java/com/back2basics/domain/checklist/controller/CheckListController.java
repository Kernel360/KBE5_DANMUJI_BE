package com.back2basics.domain.checklist.controller;

import static com.back2basics.domain.checklist.controller.code.CheckListResponseCode.CHECK_LIST_CREATE_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.CheckListResponseCode.CHECK_LIST_DELETE_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.CheckListResponseCode.CHECK_LIST_READ_ALL_SUCCESS;
import static com.back2basics.domain.checklist.controller.code.CheckListResponseCode.CHECK_LIST_UPDATE_SUCCESS;

import com.back2basics.checklist.port.in.CreateCheckListUseCase;
import com.back2basics.checklist.port.in.DeleteCheckListUseCase;
import com.back2basics.checklist.port.in.GetCheckListUseCase;
import com.back2basics.checklist.port.in.UpdateCheckListUseCase;
import com.back2basics.checklist.service.result.CheckListResult;
import com.back2basics.domain.checklist.dto.request.CreateCheckListRequest;
import com.back2basics.domain.checklist.dto.response.CheckListResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/checklist")
@RequiredArgsConstructor
public class CheckListController {

    private final CreateCheckListUseCase createCheckListUseCase;
    private final UpdateCheckListUseCase updateCheckListUseCase;
    private final DeleteCheckListUseCase deleteCheckListUseCase;
    private final GetCheckListUseCase getCheckListUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@RequestParam("postId") Long postId,
        @AuthenticationPrincipal
        CustomUserDetails userDetails, @RequestBody CreateCheckListRequest request) {
        createCheckListUseCase.create(userDetails.getId(), postId, request.toCommand());
        return ApiResponse.success(CHECK_LIST_CREATE_SUCCESS);
    }

    @PutMapping("/{checkListId}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long checkListId,
        @RequestBody CreateCheckListRequest request) {
        updateCheckListUseCase.update(checkListId, request.toCommand());
        return ApiResponse.success(CHECK_LIST_UPDATE_SUCCESS);
    }

    @PutMapping("/{checkListId}/check")
    public ResponseEntity<ApiResponse<Void>> check(@PathVariable Long checkListId) {
        updateCheckListUseCase.check(checkListId);
        return ApiResponse.success(CHECK_LIST_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{checkListId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long checkListId) {
        deleteCheckListUseCase.delete(checkListId);
        return ApiResponse.success(CHECK_LIST_DELETE_SUCCESS);
    }

    // 전체
    @GetMapping
    public ResponseEntity<ApiResponse<List<CheckListResponse>>> getByUserId(
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<CheckListResult> results = getCheckListUseCase.findByUserId(userDetails.getId());
        List<CheckListResponse> responses = results.stream().map(CheckListResponse::from).toList();
        return ApiResponse.success(CHECK_LIST_READ_ALL_SUCCESS, responses);
    }

    // 게시글 별
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<List<CheckListResponse>>> getByPostId(
        @PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<CheckListResult> results = getCheckListUseCase.findByPostId(postId,
            userDetails.getId());
        List<CheckListResponse> responses = results.stream().map(CheckListResponse::from).toList();
        return ApiResponse.success(CHECK_LIST_READ_ALL_SUCCESS, responses);
    }
}
