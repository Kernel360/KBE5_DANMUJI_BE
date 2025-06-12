package com.back2basics.project.port.in;

import com.back2basics.project.service.result.ProjectDetailResult;
import com.back2basics.project.service.result.ProjectGetResult;
import com.back2basics.project.service.result.ProjectRecentGetResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadProjectUseCase {

    // 관리자용 전체 리스트
    Page<ProjectGetResult> getAllProjects(Pageable pageable);

    List<ProjectGetResult> getAllProjects();

    // 회원용 전체 리스트
    Page<ProjectGetResult> getAllProjectsByUserId(Long userId, Pageable pageable);

    // 검색 리스트
    Page<ProjectGetResult> searchProjects(String keyword, Pageable pageable);

    // 상세 조회
    ProjectDetailResult getProjectDetails(Long projectId);

    List<ProjectRecentGetResult> getRecentProjects();
}
