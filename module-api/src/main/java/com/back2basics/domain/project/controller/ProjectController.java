package com.back2basics.domain.project.controller;

import com.back2basics.domain.project.dto.request.ProjectCreateRequest;
import com.back2basics.domain.project.dto.request.ProjectUpdateRequest;
import com.back2basics.domain.project.dto.response.ProjectGetResponse;
import com.back2basics.domain.project.dto.response.ProjectUpdateResponse;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.DeleteProjectUseCase;
import com.back2basics.project.port.in.GetProjectUseCase;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.domain.project.controller.code.ProjectResponseCode;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.project.service.result.ProjectUpdateResult;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final GetProjectUseCase getProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;


    @PostMapping()
    public ResponseEntity<ApiResponse<Void>> createProject(
        @RequestBody @Valid ProjectCreateRequest request) {
        ProjectCreateCommand command = request.toCommand();
        createProjectUseCase.createProject(command);
        return ApiResponse.success(ProjectResponseCode.PROJECT_CREATE_SUCCESS);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectGetResponse>> getProjectById(
        @PathVariable Long projectId) {
        ProjectGetResult result = getProjectUseCase.getProjectById(projectId);
        ProjectGetResponse response = ProjectGetResponse.toResponse(result);
        return ApiResponse.success(ProjectResponseCode.PROJECT_READ_SUCCESS, response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProjectGetResponse>>> getAllProjects() {
        List<ProjectGetResult> result = getProjectUseCase.getAllProjects();
        List<ProjectGetResponse> list = result.stream().map(ProjectGetResponse::toResponse)
            .collect(Collectors.toList());
        return ApiResponse.success(ProjectResponseCode.PROJECT_READ_ALL_SUCCESS, list);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectUpdateResponse>> updateProject(
        @PathVariable Long projectId,
        @RequestBody @Valid ProjectUpdateRequest request) {
        ProjectUpdateCommand command = request.toCommand();
        ProjectUpdateResult result = updateProjectUseCase.updateProject(projectId, command);
        ProjectUpdateResponse response = ProjectUpdateResponse.toResponse(result);
        return ApiResponse.success(ProjectResponseCode.PROJECT_UPDATE_SUCCESS, response);
    }

    @PatchMapping("/{projectId}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long projectId) {
        deleteProjectUseCase.deleteProject(projectId);
        return ApiResponse.success(ProjectResponseCode.PROJECT_DELETE_SUCCESS);
    }

    @PatchMapping("/{projectId}/status")
    public ResponseEntity<ApiResponse<ProjectUpdateResponse>> changedStatus(
        @PathVariable Long projectId) {
        ProjectUpdateResult result = updateProjectUseCase.changedStatus(projectId);
        ProjectUpdateResponse response = ProjectUpdateResponse.toResponse(result);
        return ApiResponse.success(ProjectResponseCode.PROJECT_UPDATE_SUCCESS, response);
    }
}
