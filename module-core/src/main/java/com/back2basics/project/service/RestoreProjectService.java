package com.back2basics.project.service;

import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.RestoreProjectUSeCase;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.project.port.out.UpdateProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestoreProjectService implements RestoreProjectUSeCase {

    private final ReadProjectPort readProjectPort;
    private final UpdateProjectPort updateProjectPort;

    // todo: 삭제 프로젝트 확인 valid
    @Override
    public void restoreProject(Long projectId) {
        Project project = readProjectPort.findDeletedProject(projectId);
        project.restore();
        updateProjectPort.update(project);
    }
}
