package com.back2basics.domain.project.controller;

import static com.back2basics.domain.project.controller.code.ProjectResponseCode.DELETED_PROJECT_READ_ALL_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_COUNT_BY_STATUS_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_CREATE_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_DELETE_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_READ_ALL_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_READ_BY_STATUS_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_READ_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_RESTORE_SUCCESS;
import static com.back2basics.domain.project.controller.code.ProjectResponseCode.PROJECT_UPDATE_SUCCESS;

import com.back2basics.domain.project.dto.request.ProjectCreateRequest;
import com.back2basics.domain.project.dto.request.ProjectSearchRequest;
import com.back2basics.domain.project.dto.request.ProjectUpdateRequest;
import com.back2basics.domain.project.dto.response.ProjectClientUserResponse;
import com.back2basics.domain.project.dto.response.ProjectCountResponse;
import com.back2basics.domain.project.dto.response.ProjectDetailResponse;
import com.back2basics.domain.project.dto.response.ProjectGetResponse;
import com.back2basics.domain.project.dto.response.ProjectListResponse;
import com.back2basics.domain.project.dto.response.ProjectRecentGetResponse;
import com.back2basics.domain.project.dto.response.ProjectStatusResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.infra.validator.UserValidator;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.DeleteProjectUseCase;
import com.back2basics.project.port.in.ReadProjectUseCase;
import com.back2basics.project.port.in.RestoreProjectUseCase;
import com.back2basics.project.port.in.SearchProjectUseCase;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.project.service.result.ProjectClientUserResult;
import com.back2basics.project.service.result.ProjectCountResult;
import com.back2basics.project.service.result.ProjectDetailResult;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.project.service.result.ProjectListResult;
import com.back2basics.project.service.result.ProjectRecentGetResult;
import com.back2basics.project.service.result.ProjectStatusResult;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.user.model.Role;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final SearchProjectUseCase searchProjectUseCase;
    private final RestoreProjectUseCase projectRestoreUseCase;
    private final UserQueryPort userQueryPort;
    private final UserValidator userValidator;

    // 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createProject(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody @Valid ProjectCreateRequest request) {
        userValidator.isAdmin(customUserDetails.getId());
        createProjectUseCase.createProject(request.toCommand(), customUserDetails.getId());
        return ApiResponse.success(PROJECT_CREATE_SUCCESS);
    }

    // 프로젝트 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProjectListResponse>>> getProjects(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        User user = userQueryPort.findById(customUserDetails.getId());
        Page<ProjectListResult> result = null;

        if (user.getRole() == Role.USER) {
            result = readProjectUseCase.getUserProjects(user.getId(),
                pageable);
        } else if (user.getRole() == Role.ADMIN) {
            result = readProjectUseCase.getAllProjects(pageable);
        }

        Page<ProjectListResponse> response = Objects.requireNonNull(result)
            .map(ProjectListResponse::toResponse);
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
    }

    // 검색 프로젝트 조회
    //todo: 키워드, 카테고리 둘 중 하나만 넣었을 때 전체가 출력됨
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProjectListResponse>>> searchProjects(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @ModelAttribute ProjectSearchRequest request,
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        User user = userQueryPort.findById(customUserDetails.getId());
        Page<ProjectListResult> result = null;
        Pageable pageable = PageRequest.of(page, size);

        if (user.getRole() == Role.USER) {
            result = searchProjectUseCase.searchUserProjects(user.getId(), request.toCommand(),
                pageable);
        } else if (user.getRole() == Role.ADMIN) {
            result = searchProjectUseCase.searchProjects(request.toCommand(), pageable);
        }
        Page<ProjectListResponse> response = Objects.requireNonNull(result)
            .map(ProjectListResponse::toResponse);

        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
    }

    // 상세 정보 조회
    // todo: api 2개로 분할
    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectDetailResponse>> getProjectDetails(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectId) {
        ProjectDetailResult result = readProjectUseCase.getProjectDetails(projectId,
            customUserDetails.getId());
        ProjectDetailResponse response = ProjectDetailResponse.toResponse(result);
        return ApiResponse.success(PROJECT_READ_SUCCESS, response);
    }

    // 수정
    @PutMapping("/{projectId}")
    public ResponseEntity<ApiResponse<Void>> updateProject(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectId,
        @RequestBody @Valid ProjectUpdateRequest request) {
        userValidator.isAdmin(customUserDetails.getId());
        ProjectUpdateCommand command = request.toCommand();
        updateProjectUseCase.updateProject(projectId, command, customUserDetails.getId());
        return ApiResponse.success(PROJECT_UPDATE_SUCCESS);
    }

    // 삭제
    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectId) {
        userValidator.isAdmin(customUserDetails.getId());
        deleteProjectUseCase.deleteProject(projectId, customUserDetails.getId());
        return ApiResponse.success(PROJECT_DELETE_SUCCESS);
    }

    // 프로젝트 상태 변경
    @PutMapping("/{projectId}/status")
    public ResponseEntity<ApiResponse<Void>> updateProjectStatus(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectId) {
        userValidator.isAdmin(customUserDetails.getId());
        updateProjectUseCase.changedStatus(projectId, customUserDetails.getId());
        return ApiResponse.success(PROJECT_UPDATE_SUCCESS);
    }

    // 프로젝트 최신순 조회
    @GetMapping("/recent-projects")
    public ResponseEntity<ApiResponse<List<ProjectRecentGetResponse>>> getRecentProjects(
    ) {
        List<ProjectRecentGetResult> result = readProjectUseCase.getRecentProjects();

        List<ProjectRecentGetResponse> response = result.stream()
            .map(ProjectRecentGetResponse::from).toList();
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
    }

    // 프로젝트 전체 조회
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProjectGetResponse>>> getProjectsWithoutPagination() {
        List<ProjectGetResult> result = readProjectUseCase.getAllProjects();

        List<ProjectGetResponse> response = result.stream()
            .map(ProjectGetResponse::toResponse)
            .toList();

        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
    }

    // 삭제된 목록 조회
    @GetMapping("/deleted")
    public ResponseEntity<ApiResponse<Page<ProjectListResponse>>> getDeletedProjects(
        @PageableDefault(
            page = 0, size = 10, sort = "deletedAt", direction = Direction.DESC
        )
        Pageable pageable) {
        Page<ProjectListResult> result = readProjectUseCase.getDeletedProjects(pageable);
        Page<ProjectListResponse> response = result.map(ProjectListResponse::toResponse);
        return ApiResponse.success(DELETED_PROJECT_READ_ALL_SUCCESS, response);
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<List<ProjectStatusResponse>>> getProjectByStatus(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestParam ProjectStatus status) {
        List<ProjectStatusResult> results = readProjectUseCase.findProjectByStatus(
            userDetails.getId(), userDetails.getRole(), status);
        List<ProjectStatusResponse> responses = results.stream().map(ProjectStatusResponse::from)
            .toList();
        return ApiResponse.success(PROJECT_READ_BY_STATUS_SUCCESS, responses);
    }

    // 상태별 개수
    @GetMapping("/status-count")
    public ResponseEntity<ApiResponse<List<ProjectCountResponse>>> getCountByProjectStatus() {
        List<ProjectCountResult> result = readProjectUseCase.getCountByProjectStatus();
        List<ProjectCountResponse> response = result.stream().map(ProjectCountResponse::toResponse)
            .toList();
        return ApiResponse.success(PROJECT_COUNT_BY_STATUS_SUCCESS, response);
    }

    @PutMapping("/{projectId}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreProject(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long projectId) {
        Long userId = customUserDetails.getId();
        userValidator.isAdmin(userId);
        projectRestoreUseCase.restoreProject(userId, projectId);
        return ApiResponse.success(PROJECT_RESTORE_SUCCESS);
    }

    @GetMapping("/{projectId}/client-user")
    public ResponseEntity<ApiResponse<List<ProjectClientUserResponse>>> getClientUsersByProjectId(
        @PathVariable Long projectId) {
        List<ProjectClientUserResult> result = readProjectUseCase.getClientUsersByProjectId(
            projectId);
        List<ProjectClientUserResponse> response = result.stream()
            .map(ProjectClientUserResponse::from)
            .toList();
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
    }
}
