package com.back2basics.adapter.persistence.project.adapter;


import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.TestPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestAdapter implements TestPort {

    private final ProjectEntityRepository projectEntityRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Page<Project> findAllByUserIdTwo(Long userId, Pageable pageable) {
        Page<ProjectEntity> projectEntities = projectEntityRepository.findAllByUserId(userId, pageable);
        Page<Project> projects = projectEntities.map(projectMapper::toDomain);
        return projects;
    }

    @Override
    public Page<Project> findAllByUserIdOne(Long userId, Pageable pageable) {
        Page<ProjectEntity> projectEntities = projectEntityRepository.findProjectsByUserIdAndIsDeletedFalse(userId, pageable);
        Page<Project> projects = projectEntities.map(projectMapper::toDomain);
        return projects;
    }
}
