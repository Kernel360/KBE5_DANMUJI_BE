package com.back2basics.adapter.persistence.project.adapter;

import com.back2basics.adapter.persistence.project.entity.ProjectEntity;
import com.back2basics.adapter.persistence.project.mapper.ProjectMapper;
import com.back2basics.adapter.persistence.project.repository.ProjectEntityRepository;
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
    public void save(Project project) {
        ProjectEntity entity = projectMapper.fromDomain(project);
        projectEntityRepository.save(entity);
    }
}
