package com.back2basics.domain.projectstep.controller;

import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_ALL_READ_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_CREATE_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_DELETE_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_READ_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_STATUS_REVERT_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_STATUS_UPDATE_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_UPDATE_SUCCESS;

import com.back2basics.domain.projectstep.dto.request.CreateProjectStepRequest;
import com.back2basics.domain.projectstep.dto.request.UpdateProjectStepRequest;
import com.back2basics.domain.projectstep.dto.response.ProjectStepResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.infra.validator.UserValidator;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.projectstep.port.in.CreateProjectStepUseCase;
import com.back2basics.projectstep.port.in.DeleteProjectStepUseCase;
import com.back2basics.projectstep.port.in.ReadProjectStepUseCase;
import com.back2basics.projectstep.port.in.UpdateProjectStepUseCase;
import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import com.back2basics.projectstep.service.result.ProjectStepResult;
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
@RequiredArgsConstructor
@RequestMapping("/api/project-steps")
public class ProjectStepController {

    private final CreateProjectStepUseCase createProjectStepUseCase;
    private final UpdateProjectStepUseCase updateProjectStepUseCase;
    private final DeleteProjectStepUseCase deleteProjectStepUseCase;
    private final ReadProjectStepUseCase readProjectStepUseCase;

    private final UpdateProjectUseCase updateProjectUseCase;
    private final UserValidator userValidator;

    //todo: 권한확인 로직 이동
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createStep(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody CreateProjectStepRequest request,
        @RequestParam Long projectId) {
        CreateProjectStepCommand command = request.toCommand();
        userValidator.isAdminOrDeveloperOrManager(customUserDetails.getId(), projectId);
        createProjectStepUseCase.createStep(command, projectId, customUserDetails.getId());
        updateProjectUseCase.calculateProgressRate(projectId);

        return ApiResponse.success(STEP_CREATE_SUCCESS);
    }

    // 단계 순서 변경
    @PutMapping("/{projectId}/reorder")
    public ResponseEntity<ApiResponse<Void>> reorderSteps(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody List<Long> stepIdsInNewOrder,
        @PathVariable Long projectId) {
        userValidator.isAdminOrDeveloperOrManager(customUserDetails.getId(), projectId);
        updateProjectStepUseCase.reorderSteps(projectId, stepIdsInNewOrder);
        return ApiResponse.success(STEP_UPDATE_SUCCESS);
    }

    // 프로젝트 별 단계 목록
    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<List<ProjectStepResponse>>> getStepsByProjectId(
        @PathVariable Long projectId) {
        List<ProjectStepResult> result = readProjectStepUseCase.findAllByProjectId(
            projectId);
        List<ProjectStepResponse> response = result.stream()
            .map(ProjectStepResponse::toResponse).toList();
        return ApiResponse.success(STEP_ALL_READ_SUCCESS, response);
    }

    // 단계 상세 조회
    @GetMapping("/{stepId}/detail")
    public ResponseEntity<ApiResponse<ProjectStepResponse>> getStepById(
        @PathVariable Long stepId) {
        ProjectStepResult result = readProjectStepUseCase.findById(stepId);
        ProjectStepResponse response = ProjectStepResponse.toResponse(result);
        return ApiResponse.success(STEP_READ_SUCCESS, response);
    }

    // 수정
    //todo: 프론트에서 projectId 넘기기
    @PutMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> updateStepName(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody UpdateProjectStepRequest request,
        @RequestParam Long projectId,
        @PathVariable Long stepId) {
        userValidator.isAdminOrDeveloperOrManager(customUserDetails.getId(), projectId);
        UpdateProjectStepCommand command = request.toCommand();
        updateProjectStepUseCase.updateStepName(command, stepId, customUserDetails.getId());
        return ApiResponse.success(STEP_UPDATE_SUCCESS);
    }

    // 삭제
    @DeleteMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> deleteStep(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long stepId,
        @RequestParam Long projectId) {
        userValidator.isAdminOrDeveloperOrManager(customUserDetails.getId(), projectId);
        deleteProjectStepUseCase.softDelete(stepId, customUserDetails.getId());
        updateProjectUseCase.calculateProgressRateByDeleteStep(projectId);
        return ApiResponse.success(STEP_DELETE_SUCCESS);
    }

    // 단계 상태 변경
    @PutMapping("/{stepId}/status")
    public ResponseEntity<ApiResponse<Void>> updateStepStatus(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestParam Long projectId,
        @PathVariable Long stepId
    ) {
        userValidator.isAdminOrDeveloperOrManager(customUserDetails.getId(), projectId);
        updateProjectStepUseCase.updateStepStatus(stepId);
        updateProjectUseCase.calculateProgressRate(projectId);
        return ApiResponse.success(STEP_STATUS_UPDATE_SUCCESS);
    }

    // ProjectStepStatus PENDING 으로 초기화
    //todo: 프론트에서 projectId 넘기기
    @PutMapping("/{stepId}/revert")
    public ResponseEntity<ApiResponse<Void>> revertStepStatus(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestParam Long projectId,
        @PathVariable Long stepId) {
        userValidator.isAdminOrDeveloperOrManager(customUserDetails.getId(), projectId);
        updateProjectStepUseCase.revertStepStatus(stepId);
        return ApiResponse.success(STEP_STATUS_REVERT_SUCCESS);
    }
}
