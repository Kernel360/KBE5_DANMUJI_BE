package com.back2basics.project.port.in;

import com.back2basics.project.port.in.command.ProjectSearchCommand;
import com.back2basics.project.service.result.ProjectListResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProjectUseCase {
    // 전체 검색 리스트
    Page<ProjectListResult> searchProjects(ProjectSearchCommand command, Pageable pageable);

    // 회원별 검색 리스트
    Page<ProjectListResult> searchUserProjects(Long userId, ProjectSearchCommand command, Pageable pageable);

}
