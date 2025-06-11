package com.back2basics.domain.projectstep.controller;

import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_READ_SUCCESS;

import com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode;
import com.back2basics.domain.projectstep.dto.request.CreateProjectStepRequest;
import com.back2basics.domain.projectstep.dto.request.UpdateProjectStepRequest;
import com.back2basics.domain.projectstep.dto.response.DetailProjectStepResponse;
import com.back2basics.domain.projectstep.dto.response.ProjectStepSimpleResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.port.in.CreateProjectStepUseCase;
import com.back2basics.projectstep.port.in.DeleteProjectStepUseCase;
import com.back2basics.projectstep.port.in.ReadProjectStepUseCase;
import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepUseCase;
import com.back2basics.projectstep.service.result.DetailProjectStepResult;
import com.back2basics.projectstep.service.result.ProjectStepSimpleResult;
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
@RequestMapping("/api/step")
public class ProjectStepController {

    private final CreateProjectStepUseCase createProjectStepUseCase;
    private final UpdateProjectStepUseCase updateProjectStepUseCase;
    private final DeleteProjectStepUseCase deleteProjectStepUseCase;
    private final ReadProjectStepUseCase readProjectStepUseCase;

    // param
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createStep(
        @RequestBody CreateProjectStepRequest request, @RequestParam Long projectId) {
        CreateProjectStepCommand command = request.toCommand();
        createProjectStepUseCase.createStep(command, projectId);
        return ApiResponse.success(ProjectStepResponseCode.STEP_CREATE_SUCCESS);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<List<DetailProjectStepResponse>>> getStepsProjectId(
        @PathVariable Long projectId) {
        List<DetailProjectStepResult> result = readProjectStepUseCase.findDetailByProjectId(projectId);
        List<DetailProjectStepResponse> response = result.stream()
            .map(DetailProjectStepResponse::toResponse).toList();
        return ApiResponse.success(STEP_READ_SUCCESS, response);
    }

    @PutMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> updateStep(@PathVariable Long stepId, @RequestBody
    UpdateProjectStepRequest request) {
        UpdateProjectStepCommand command = request.toCommand();
        updateProjectStepUseCase.updateStep(command, stepId);
        return ApiResponse.success(ProjectStepResponseCode.STEP_UPDATE_SUCCESS);
    }

    // todo: RequestParam 으로 받았는데 RequestBody 도 가능
    @PutMapping("/{stepId}/approval")
    public ResponseEntity<ApiResponse<Void>> updateApprovalStatus(@PathVariable Long stepId,
        @RequestParam
        ProjectFeedbackStepStatus projectFeedbackStepStatus) {
        updateProjectStepUseCase.updateApprovalStatus(projectFeedbackStepStatus, stepId);
        return ApiResponse.success(ProjectStepResponseCode.STEP_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> deleteStep(@PathVariable Long stepId) {
        deleteProjectStepUseCase.softDelete(stepId);
        return ApiResponse.success(ProjectStepResponseCode.STEP_DELETE_SUCCESS);
    }
}
