package com.back2basics.project.port.in;

import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.port.in.command.ProjectSearchCommand;
import com.back2basics.project.service.result.ProjectCountResult;
import com.back2basics.project.service.result.ProjectDetailResult;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.project.service.result.ProjectListResult;
import com.back2basics.project.service.result.ProjectRecentGetResult;
import com.back2basics.project.service.result.ProjectStatusResult;
import com.back2basics.user.model.Role;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadProjectUseCase {

    // 전체 프로젝트 목록
    Page<ProjectListResult> getAllProjects(Pageable pageable);

    // 회원별 프로젝트 목록
    Page<ProjectListResult> getUserProjects(Long userId, Pageable pageable);

    // 상세 조회
    ProjectDetailResult getProjectDetails(Long projectId, Long userId);

    // 삭제 목록
    Page<ProjectListResult> getDeletedProjects(Pageable pageable);

    List<ProjectGetResult> getAllProjects();

    List<ProjectRecentGetResult> getRecentProjects();

    List<ProjectStatusResult> findProjectByStatus(Long userId, Role role, ProjectStatus status);

    List<ProjectCountResult> getCountByProjectStatus();
}
