package com.back2basics.domain.project.controller;

import com.back2basics.domain.project.dto.request.ProjectCreateRequest;
import com.back2basics.domain.project.dto.request.ProjectUpdateRequest;
import com.back2basics.domain.project.dto.response.ProjectGetResponse;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.DeleteProjectUseCase;
import com.back2basics.project.port.in.ReadProjectUseCase;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.domain.project.controller.code.ProjectResponseCode;
import com.back2basics.project.service.result.ProjectGetResult;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final ReadProjectUseCase readProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUseCase;

    // todo: transactional을 여기 붙이는게 아닌가. 아까 했을 때 안통하던데.....
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<Void>> createProject(
        @RequestBody @Valid ProjectCreateRequest request) {
        ProjectCreateCommand command = request.toCommand();
        createProjectUseCase.createProject(command);
        return ApiResponse.success(ProjectResponseCode.PROJECT_CREATE_SUCCESS);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectGetResponse>> getProjectById(
        @PathVariable Long projectId) {
        ProjectGetResult result = readProjectUseCase.getProjectById(projectId);
        ProjectGetResponse response = ProjectGetResponse.toResponse(result);
        return ApiResponse.success(ProjectResponseCode.PROJECT_READ_SUCCESS, response);
    }

    // todo: paging 적용
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProjectGetResponse>>> getAllProjects(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectGetResult> result = readProjectUseCase.getAllProjects(pageable);
        Page<ProjectGetResponse> list = result.map(ProjectGetResponse::toResponse);
        return ApiResponse.success(ProjectResponseCode.PROJECT_READ_ALL_SUCCESS, list);
    }

    // todo: log 조회 - 삭제프로젝트 / 수정프로젝트는 어떠케 ..? - 수정이 너무 다양한데.. 고민..

    // todo: search + paging list
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProjectGetResponse>>> searchProjects(
        @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectGetResult> resultPage = readProjectUseCase.searchProjects(keyword, pageable);
        Page<ProjectGetResponse> responsePage = resultPage.map(ProjectGetResponse::toResponse);

        return ApiResponse.success(ProjectResponseCode.PROJECT_READ_ALL_SUCCESS, responsePage);
    }


    @PutMapping("/{projectId}")
    public ResponseEntity<ApiResponse<Void>> updateProject(
        @PathVariable Long projectId,
        @RequestBody @Valid ProjectUpdateRequest request) {
        ProjectUpdateCommand command = request.toCommand();
        updateProjectUseCase.updateProject(projectId, command);
        return ApiResponse.success(ProjectResponseCode.PROJECT_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long projectId) {
        deleteProjectUseCase.deleteProject(projectId);
        return ApiResponse.success(ProjectResponseCode.PROJECT_DELETE_SUCCESS);
    }

    @PutMapping("/{projectId}/status")
    public ResponseEntity<ApiResponse<Void>> changedStatus(
        @PathVariable Long projectId) {
        updateProjectUseCase.changedStatus(projectId);
        return ApiResponse.success(ProjectResponseCode.PROJECT_UPDATE_SUCCESS);
    }
}
