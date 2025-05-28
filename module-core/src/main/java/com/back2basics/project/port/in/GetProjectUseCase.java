package com.back2basics.project.port.in;

import com.back2basics.project.port.in.command.ProjectResponseDto;
import java.util.List;

public interface GetProjectUseCase {

    /* 프로젝트 상세 조회 */
    ProjectResponseDto getProjectById(Long projectId);

    /* 관리자용 전체 리스트 (나중에 검색 -> 카테고리 : 프로젝트명, 회원명)
        프로젝트 상태 -> 전체, 진행중, 완료 */
    List<ProjectResponseDto> getAllProjects();

    /* 회원용 전체 리스트 (나중에 검색 -> 카테고리 : 프로젝트명)
        프로젝트 상태 -> 전체, 진행중, 완료 */
    List<ProjectResponseDto> getAllProjectsByUserId(Long userId);
}
