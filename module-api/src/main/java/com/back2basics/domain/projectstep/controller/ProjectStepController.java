package com.back2basics.domain.projectstep.controller;

import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_ALL_READ_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_CREATE_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_DELETE_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_READ_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_UPDATE_SUCCESS;

import com.back2basics.domain.projectstep.dto.request.CreateProjectStepRequest;
import com.back2basics.domain.projectstep.dto.request.UpdateProjectStepRequest;
import com.back2basics.domain.projectstep.dto.response.ProjectStepResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.in.CreateProjectStepUseCase;
import com.back2basics.projectstep.port.in.DeleteProjectStepUseCase;
import com.back2basics.projectstep.port.in.ReadProjectStepUseCase;
import com.back2basics.projectstep.port.in.UpdateProjectStepUseCase;
import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import com.back2basics.projectstep.service.result.ProjectStepResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/projects/steps")
public class ProjectStepController {

    private final CreateProjectStepUseCase createProjectStepUseCase;
    private final UpdateProjectStepUseCase updateProjectStepUseCase;
    private final DeleteProjectStepUseCase deleteProjectStepUseCase;
    private final ReadProjectStepUseCase readProjectStepUseCase;

    // /api/projects/steps?projectId={projectId}
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createStep(
        @RequestBody CreateProjectStepRequest request, @RequestParam Long projectId) {
        CreateProjectStepCommand command = request.toCommand();
        createProjectStepUseCase.createStep(command, projectId);
        return ApiResponse.success(STEP_CREATE_SUCCESS);
    }

    @PutMapping("/{projectId}/reorder")
    public ResponseEntity<ApiResponse<Void>> reorderSteps(@PathVariable Long projectId,
        @RequestBody List<Long> stepIdsInNewOrder) {
        updateProjectStepUseCase.reorderSteps(projectId, stepIdsInNewOrder);
        return ApiResponse.success(STEP_UPDATE_SUCCESS);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<List<ProjectStepResponse>>> getStepsProjectId(
        @PathVariable Long projectId) {
        List<ProjectStepResult> result = readProjectStepUseCase.findByProjectId(
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
    @PutMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> updateStepName(@PathVariable Long stepId, @RequestBody
    UpdateProjectStepRequest request) {
        UpdateProjectStepCommand command = request.toCommand();
        updateProjectStepUseCase.updateStepName(command, stepId);
        return ApiResponse.success(STEP_UPDATE_SUCCESS);
    }

    // todo: 승인, 거절 버튼마다 url - projectStepStatus 다르게
    @PutMapping("/{stepId}/approval")
    public ResponseEntity<ApiResponse<Void>> updateApprovalStatus(@PathVariable Long stepId,
        @RequestParam
        ProjectStepStatus projectStepStatus) {
        updateProjectStepUseCase.updateApprovalStatus(projectStepStatus, stepId);
        return ApiResponse.success(STEP_UPDATE_SUCCESS);
    }

    // 삭제
    @DeleteMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> deleteStep(@PathVariable Long stepId) {
        deleteProjectStepUseCase.softDelete(stepId);
        return ApiResponse.success(STEP_DELETE_SUCCESS);
    }
}
