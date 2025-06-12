package com.back2basics.domain.project.controller;

import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_CREATE_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_DELETE_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_READ_ALL_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_READ_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_UPDATE_SUCCESS;

import com.back2basics.domain.project.dto.request.ProjectCreateRequest;
import com.back2basics.domain.project.dto.request.ProjectUpdateRequest;
import com.back2basics.domain.project.dto.response.ProjectDetailResponse;
import com.back2basics.domain.project.dto.response.ProjectGetResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.DeleteProjectUseCase;
import com.back2basics.project.port.in.ReadProjectUseCase;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.project.service.result.ProjectDetailResult;
import com.back2basics.project.service.result.ProjectGetResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    // todo: 변수명 통일
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createProject(
        @RequestBody @Valid ProjectCreateRequest request) {
        createProjectUseCase.createProject(request.toCommand());
        return ApiResponse.success(PROJECT_CREATE_SUCCESS);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProjectGetResponse>>> getAllProjects(
        @PageableDefault(
            page = 0,
            size = 10
        )
        Pageable pageable) {
        Page<ProjectGetResult> result = readProjectUseCase.getAllProjects(pageable);
        Page<ProjectGetResponse> list = result.map(ProjectGetResponse::toResponse);
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, list);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProjectGetResponse>>> searchProjects(
        @RequestParam(required = false) String keyword,

        @PageableDefault(
            page = 0,
            size = 10
        )
        Pageable pageable) {
        Page<ProjectGetResult> resultPage = readProjectUseCase.searchProjects(keyword, pageable);
        Page<ProjectGetResponse> responsePage = resultPage.map(ProjectGetResponse::toResponse);

        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, responsePage);
    }

    // 회원 별 프로젝트 목록
    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse<Page<ProjectGetResponse>>> getAllProjectsById(
        @PageableDefault(
            page = 0,
            size = 10
        )
        Pageable pageable, @PathVariable Long userId) {
        Page<ProjectGetResult> result = readProjectUseCase.getAllProjectsByUserId(userId, pageable);
        Page<ProjectGetResponse> list = result.map(ProjectGetResponse::toResponse);
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, list);
    }

    // 상세 정보 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectDetailResponse>> getProjectDetails(
        @PathVariable Long projectId) {
        ProjectDetailResult result = readProjectUseCase.getProjectDetails(projectId);
        ProjectDetailResponse response = ProjectDetailResponse.from(result);
        return ApiResponse.success(PROJECT_READ_SUCCESS, response);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ApiResponse<Void>> updateProject(
        @PathVariable Long projectId,
        @RequestBody @Valid ProjectUpdateRequest request) {
        ProjectUpdateCommand command = request.toCommand();
        updateProjectUseCase.updateProject(projectId, command);
        return ApiResponse.success(PROJECT_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long projectId) {
        deleteProjectUseCase.deleteProject(projectId);
        return ApiResponse.success(PROJECT_DELETE_SUCCESS);
    }

    @PutMapping("/{projectId}/status")
    public ResponseEntity<ApiResponse<Void>> changedStatus(
        @PathVariable Long projectId) {
        updateProjectUseCase.changedStatus(projectId);
        return ApiResponse.success(PROJECT_UPDATE_SUCCESS);
    }

    // todo: log 조회 - 삭제프로젝트 / 수정프로젝트는 어떠케 ..? - 수정이 너무 다양한데.. 고민.. -> 5순위

}
