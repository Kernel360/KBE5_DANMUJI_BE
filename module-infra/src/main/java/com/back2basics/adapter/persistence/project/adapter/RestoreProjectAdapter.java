package com.back2basics.adapter.persistence.project.adapter;

import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;

import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.project.port.out.RestoreProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestoreProjectAdapter implements RestoreProjectPort {

    private final ProjectEntityRepository projectRepository;
    private final ProjectMapper mapper;
    private final ReadProjectPort readProjectPort;

    @Override
    public void restoreProject(Project project) {
        Project deletedProject = readProjectPort.findDeletedProjectById(project.getId())
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));

        ProjectEntity entity = mapper.toEntity(deletedProject);
        entity.restoreProject();

        projectRepository.save(entity);
    }
}

