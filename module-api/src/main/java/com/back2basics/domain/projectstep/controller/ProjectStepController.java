package com.back2basics.domain.projectstep.controller;

import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_ALL_READ_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_CREATE_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_DELETE_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_READ_SUCCESS;
import static com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode.STEP_UPDATE_SUCCESS;

import com.back2basics.domain.projectstep.dto.request.CreateProjectStepRequest;
import com.back2basics.domain.projectstep.dto.request.UpdateProjectStepRequest;
import com.back2basics.domain.projectstep.dto.response.DetailProjectStepResponse;
import com.back2basics.domain.projectstep.dto.response.ReadProjectStepResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.projectstep.model.ProjectFeedbackStepStatus;
import com.back2basics.projectstep.port.in.CreateProjectStepUseCase;
import com.back2basics.projectstep.port.in.DeleteProjectStepUseCase;
import com.back2basics.projectstep.port.in.ReadProjectStepUseCase;
import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepUseCase;
import com.back2basics.projectstep.service.result.DetailProjectStepResult;
import com.back2basics.projectstep.service.result.ReadProjectStepResult;
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

    // /api/step?projectId={projectId}
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createStep(
        @RequestBody CreateProjectStepRequest request, @RequestParam Long projectId) {
        System.out.println("stepOrder in request DTO = " + request.stepOrder()); // 이거 하니까 왜 되는거임 ;;
        CreateProjectStepCommand command = request.toCommand();
        createProjectStepUseCase.createStep(command, projectId);
        return ApiResponse.success(STEP_CREATE_SUCCESS);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addStep(

    ) {

    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<List<DetailProjectStepResponse>>> getStepsProjectId(
        @PathVariable Long projectId) {
        List<DetailProjectStepResult> result = readProjectStepUseCase.findDetailByProjectId(
            projectId);
        List<DetailProjectStepResponse> response = result.stream()
            .map(DetailProjectStepResponse::toResponse).toList();
        return ApiResponse.success(STEP_ALL_READ_SUCCESS, response);
    }

    @GetMapping("/{stepId}")
    public ResponseEntity<ApiResponse<ReadProjectStepResponse>> getStepById(
        @PathVariable Long stepId) {
        ReadProjectStepResult result = readProjectStepUseCase.findById(stepId);
        ReadProjectStepResponse response = ReadProjectStepResponse.toResponse(result);
        return ApiResponse.success(STEP_READ_SUCCESS, response);
    }

    @PutMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> updateStep(@PathVariable Long stepId, @RequestBody
    UpdateProjectStepRequest request) {
        UpdateProjectStepCommand command = request.toCommand();
        updateProjectStepUseCase.updateStep(command, stepId);
        return ApiResponse.success(STEP_UPDATE_SUCCESS);
    }

    // todo: 승인, 거절 버튼마다 url - projectFeedbackStepStatus 다르게
    @PutMapping("/{stepId}/approval")
    public ResponseEntity<ApiResponse<Void>> updateApprovalStatus(@PathVariable Long stepId,
        @RequestParam
        ProjectFeedbackStepStatus projectFeedbackStepStatus) {
        updateProjectStepUseCase.updateApprovalStatus(projectFeedbackStepStatus, stepId);
        return ApiResponse.success(STEP_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> deleteStep(@PathVariable Long stepId) {
        deleteProjectStepUseCase.softDelete(stepId);
        return ApiResponse.success(STEP_DELETE_SUCCESS);
    }
}
