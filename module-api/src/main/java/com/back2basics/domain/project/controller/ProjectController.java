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
import com.back2basics.domain.project.dto.response.ProjectRecentGetResponse;
import com.back2basics.domain.project.dto.response.ProjectListResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.DeleteProjectUseCase;
import com.back2basics.project.port.in.ReadProjectUseCase;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.project.service.result.ProjectDetailResult;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.project.service.result.ProjectRecentGetResult;
import com.back2basics.project.service.result.ProjectListResult;
import jakarta.validation.Valid;
import java.util.List;
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
    // todo: 변수명 통일, response 세분화

    // 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createProject(
        @RequestBody @Valid ProjectCreateRequest request) {
        createProjectUseCase.createProject(request.toCommand());
        return ApiResponse.success(PROJECT_CREATE_SUCCESS);
    }

    // 회원별 프로젝트 목록, 양방향 연관관계
    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse<Page<ProjectListResponse>>> getUserProjects(
        @PathVariable Long userId,
        @PageableDefault(
            page = 0,
            size = 10
        )
        Pageable pageable) {

        Page<ProjectListResult> result = readProjectUseCase.getUserProjects(userId, pageable);
        Page<ProjectListResponse> response = result.map(ProjectListResponse::toResponse);
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
    }

    // 전체 프로젝트 목록
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProjectGetResponse>>> getProjects(
        @PageableDefault(
            page = 0,
            size = 10
        )
        Pageable pageable) {
        Page<ProjectGetResult> result = readProjectUseCase.getAllProjects(pageable);
        Page<ProjectGetResponse> response = result.map(ProjectGetResponse::toResponse);
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
    }

    // 검색 프로젝트 조회
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProjectGetResponse>>> searchProjects(
        @RequestParam(required = false) String keyword,
        @PageableDefault(
            page = 0,
            size = 10
        )
        Pageable pageable) {
        Page<ProjectGetResult> result = readProjectUseCase.searchProjects(keyword, pageable);
        Page<ProjectGetResponse> response = result.map(ProjectGetResponse::toResponse);

        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
    }

    // 상세 정보 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<ProjectDetailResponse>> getProjectDetails(
        @PathVariable Long projectId) {
        ProjectDetailResult result = readProjectUseCase.getProjectDetails(projectId);
        ProjectDetailResponse response = ProjectDetailResponse.toResponse(result);
        return ApiResponse.success(PROJECT_READ_SUCCESS, response);
    }

    // 수정 todo: member 제거
    @PutMapping("/{projectId}")
    public ResponseEntity<ApiResponse<Void>> updateProject(
        @PathVariable Long projectId,
        @RequestBody @Valid ProjectUpdateRequest request) {
        ProjectUpdateCommand command = request.toCommand();
        updateProjectUseCase.updateProject(projectId, command);
        return ApiResponse.success(PROJECT_UPDATE_SUCCESS);
    }

    // 삭제
    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long projectId) {
        deleteProjectUseCase.deleteProject(projectId);
        return ApiResponse.success(PROJECT_DELETE_SUCCESS);
    }

    // 프로젝트 상태 변경
    @PutMapping("/{projectId}/status")
    public ResponseEntity<ApiResponse<Void>> updateProjectStatus(
        @PathVariable Long projectId) {
        updateProjectUseCase.changedStatus(projectId);
        return ApiResponse.success(PROJECT_UPDATE_SUCCESS);
    }

    // 프로젝트 최신순 조회 <- 프로젝트 전체 조회만 사용하고 top5는 프론트에서 처리하는 게 좋을 듯 api 중복되는 느낌
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

    // todo: log 조회 - 삭제프로젝트 / 수정프로젝트는 어떠케 ..? - 수정이 너무 다양한데.. 고민.. -> 5순위

    /* todo: 회원 별 프로젝트 목록 - 단반향 연관관계
        현재 양방향으로 해놓았으나 단방향도 가능할 것으로 생각됨. 추후 테스트 해보고 단방향으로 변경 */
//    @GetMapping("/{userId}/test")
//    public ResponseEntity<ApiResponse<Page<TestResponse>>> getAllByUserIdOne(
//        @PathVariable Long userId,
//        @PageableDefault(
//            page = 0,
//            size = 10
//        )
//        Pageable pageable) {
//
//        Page<TestResult> result = readProjectUseCase.getAllByUserIdOne(userId, pageable);
//        Page<TestResponse> response = result.map(TestResponse::toResponse);
//        System.out.println("=== 단방향 === ");
//        System.out.println("유저아이디: " + userId);
//        System.out.println("페이저블: " + pageable);
//        System.out.println("response 개수: " + response.stream().count());
//        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
//    }
}
