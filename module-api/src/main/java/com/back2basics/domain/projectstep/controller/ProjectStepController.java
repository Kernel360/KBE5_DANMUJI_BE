package com.back2basics.domain.projectstep.controller;

import com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode;
import com.back2basics.domain.projectstep.dto.request.CreateProjectStepRequest;
import com.back2basics.domain.projectstep.dto.request.UpdateProjectStepRequest;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.projectstep.port.in.CreateProjectStepUseCase;
import com.back2basics.projectstep.port.in.DeleteProjectStepUseCase;
import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepCommand;
import com.back2basics.projectstep.port.in.command.UpdateProjectStepUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
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

    // param
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createStep(
        @RequestBody CreateProjectStepRequest request, @RequestParam Long projectId) {
        CreateProjectStepCommand command = request.toCommand();
        createProjectStepUseCase.createStep(command, projectId);
        return ApiResponse.success(ProjectStepResponseCode.STEP_CREATE_SUCCESS);
    }

    @PutMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> updateStep(@PathVariable Long stepId, @RequestBody
    UpdateProjectStepRequest request) {
        UpdateProjectStepCommand command = request.toCommand();
        updateProjectStepUseCase.updateStep(command, stepId);
        return ApiResponse.success(ProjectStepResponseCode.STEP_UPDATE_SUCCESS);
    }

    @PatchMapping("/{stepId}")
    public ResponseEntity<ApiResponse<Void>> deleteStep(@PathVariable Long stepId) {
        deleteProjectStepUseCase.softDelete(stepId);
        return ApiResponse.success(ProjectStepResponseCode.STEP_DELETE_SUCCESS);
    }
}
