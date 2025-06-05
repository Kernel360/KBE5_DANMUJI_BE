package com.back2basics.project.port.in;

import com.back2basics.project.service.result.ProjectGetResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadProjectUseCase {

    /* 프로젝트 상세 조회 */
    ProjectGetResult getProjectById(Long projectId);

    /* 관리자용 전체 리스트 (나중에 검색 -> 카테고리 : 프로젝트명, 회원명)
        프로젝트 상태 -> 전체, 진행중, 완료 */
    Page<ProjectGetResult> getAllProjects(Pageable pageable);

    Page<ProjectGetResult> searchProjects(String keyword, Pageable pageable);

    /* 회원용 전체 리스트 (나중에 검색 -> 카테고리 : 프로젝트명)
        프로젝트 상태 -> 전체, 진행중, 완료 */
    List<ProjectGetResult> getAllProjectsByUserId(Long userId);
}
