package com.back2basics.adapter.persistence.project.adapter;

import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.SaveProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveProjectAdapter implements SaveProjectPort {
    private final ProjectEntityRepository projectEntityRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Project save(Project project) {
        ProjectEntity entity = projectMapper.fromDomain(project);
        projectEntityRepository.save(entity);
        return projectMapper.toDomain(entity);
    }
}
