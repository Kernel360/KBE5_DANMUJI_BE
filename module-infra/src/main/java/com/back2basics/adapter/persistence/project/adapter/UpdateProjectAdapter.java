package com.back2basics.adapter.persistence.project.adapter;

import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.UpdateProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectAdapter implements UpdateProjectPort {

    private final ProjectEntityRepository projectEntityRepository;
    private final ProjectMapper projectMapper;

    @Override
    public void update(Project project) {
        projectEntityRepository.save(projectMapper.fromDomain(project));
    }
}
