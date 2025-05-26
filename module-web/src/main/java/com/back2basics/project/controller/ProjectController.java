package com.back2basics.project.controller;

import com.back2basics.port.in.project.CreateProjectUseCase;

import com.back2basics.port.in.project.DeleteProjectUseCase;
import com.back2basics.port.in.project.GetProjectUseCase;
import com.back2basics.port.in.project.UpdateProjectUseCase;
import com.back2basics.response.global.result.ApiResponse;
import com.back2basics.service.project.dto.ProjectCreateCommand;
import com.back2basics.service.project.dto.ProjectResponseDto;
import com.back2basics.service.project.dto.ProjectUpdateCommand;
import com.back2basics.service.project.response.ProjectResponseCode;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final GetProjectUseCase getProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;


    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> createProject(@RequestBody @Valid ProjectCreateCommand command) {
        createProjectUseCase.createProject(command);
        return ApiResponse.success(ProjectResponseCode.PROJECT_CREATE_SUCCESS);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectResponseDto>> getProjectById(@PathVariable Long projectId) {
        ProjectResponseDto projectDto = getProjectUseCase.getProjectById(projectId);
        return ApiResponse.success(ProjectResponseCode.PROJECT_READ_SUCCESS, projectDto);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ProjectResponseDto>>> getAllProjects() {
        List<ProjectResponseDto> list = getProjectUseCase.getAllProjects();
        return ApiResponse.success(ProjectResponseCode.PROJECT_READ_ALL_SUCCESS, list);
    }

    // todo : 수정로그체크하려면 id , 변경사항 리턴
    @PatchMapping("/{projectId}")
    public ResponseEntity<ApiResponse<Void>> updateProject(
        @PathVariable Long projectId,
        @RequestBody @Valid ProjectUpdateCommand command) {
        updateProjectUseCase.updateProject(projectId, command);
        return ApiResponse.success(ProjectResponseCode.PROJECT_UPDATE_SUCCESS);
    }

    @PatchMapping("/{projectId}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long projectId) {
        deleteProjectUseCase.deleteProject(projectId);
        return ApiResponse.success(ProjectResponseCode.PROJECT_DELETE_SUCCESS);
    }
}
