package com.back2basics.port.in.project;

import com.back2basics.service.project.dto.ProjectResponseDto;
import com.back2basics.service.project.dto.ProjectUpdateCommand;

public interface UpdateProjectUseCase {
    ProjectResponseDto updateProject(Long projectId, ProjectUpdateCommand projectUpdateCommand); // 프로젝트명, 프로젝트설명, 시작일, 마감일 변경
}
