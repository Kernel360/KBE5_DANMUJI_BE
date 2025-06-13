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
import com.back2basics.domain.project.dto.response.TestResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.DeleteProjectUseCase;
import com.back2basics.project.port.in.ReadProjectUseCase;
import com.back2basics.project.port.in.TestUseCase;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.project.service.result.ProjectDetailResult;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.project.service.result.ProjectRecentGetResult;
import com.back2basics.project.service.result.TestResult;
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
    private final TestUseCase testUseCase;


    // 회원별 프로젝트 목록 테스트 -> 양방향 연관관계
    @GetMapping("/{userId}/twowaytest")
    public ResponseEntity<ApiResponse<Page<TestResponse>>> getAllByUserIdTwo(
        @PathVariable Long userId,
        @PageableDefault(
            page = 0,
            size = 10
        )
        Pageable pageable) {

        Page<TestResult> result = testUseCase.getAllByUserIdTwo(userId, pageable);
        Page<TestResponse> response = result.map(TestResponse::toResponse);
        System.out.println("=== 양방향 === ");
        System.out.println("유저아이디: " + userId);
        System.out.println("페이저블: " + pageable);
        System.out.println("response 개수: " + response.stream().count());
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);

        /* 예상 System.out.println();
            유저아이디: 5
            페이지: 0사이즈: 10
            페이저블: Page request [number: 0, size 10, sort: UNSORTED]
            response 개수: 3
         */
    }

    // 회원 별 프로젝트 목록 - 단반향 연관관계
    @GetMapping("/{userId}/test")
    public ResponseEntity<ApiResponse<Page<TestResponse>>> getAllByUserIdOne(
        @PathVariable Long userId,
        @PageableDefault(
            page = 0,
            size = 10
        )
        Pageable pageable) {

        Page<TestResult> result = testUseCase.getAllByUserIdOne(userId, pageable);
        Page<TestResponse> response = result.map(TestResponse::toResponse);
        System.out.println("=== 단방향 === ");
        System.out.println("유저아이디: " + userId);
        System.out.println("페이저블: " + pageable);
        System.out.println("response 개수: " + response.stream().count());
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
    }

    // todo: 변수명 통일
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createProject(
        @RequestBody @Valid ProjectCreateRequest request) {
        createProjectUseCase.createProject(request.toCommand());
        System.out.println("project 이름:" + request.name());
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
        Page<ProjectGetResponse> response = result.map(ProjectGetResponse::toResponse);
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, response);
    }

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
        ProjectDetailResponse response = ProjectDetailResponse.from(result);
        return ApiResponse.success(PROJECT_READ_SUCCESS, response);
    }

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
        System.out.println("== 상태변경 아이디 == " + projectId);
        return ApiResponse.success(PROJECT_UPDATE_SUCCESS);
    }

    @GetMapping("/recent-projects")
    public ResponseEntity<ApiResponse<List<ProjectRecentGetResponse>>> getRecentProjects(
    ) {
        List<ProjectRecentGetResult> projects = readProjectUseCase.getRecentProjects();

        List<ProjectRecentGetResponse> responseList = projects.stream()
            .map(ProjectRecentGetResponse::from).toList();
        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS,
            responseList);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProjectGetResponse>>> getAllProjectsWithoutPagination() {
        List<ProjectGetResult> projects = readProjectUseCase.getAllProjects();

        List<ProjectGetResponse> responseList = projects.stream()
            .map(ProjectGetResponse::toResponse)
            .toList();

        return ApiResponse.success(PROJECT_READ_ALL_SUCCESS, responseList);
    }

    // todo: log 조회 - 삭제프로젝트 / 수정프로젝트는 어떠케 ..? - 수정이 너무 다양한데.. 고민.. -> 5순위

}
