package com.back2basics.project.adapter.out;

import com.back2basics.model.project.Project;
import com.back2basics.port.out.project.ProjectRepositoryPort;
import com.back2basics.post.repository.PostEntityRepository;
import com.back2basics.project.entity.ProjectEntity;
import com.back2basics.project.mapper.ProjectMapper;
import com.back2basics.project.repository.ProjectEntityRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectJpaAdapter implements ProjectRepositoryPort {

    private final ProjectEntityRepository projectEntityRepository;
    private final ProjectMapper projectMapper;

    @Override
    public void save(Project project) {
        ProjectEntity entity = projectMapper.fromDomain(project);
        projectEntityRepository.save(entity);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectEntityRepository.findById(id).map(projectMapper::toDomain);
    }

    @Override
    public List<Project> findAll() {
        return projectEntityRepository.findAll().stream()
            .map(projectMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void update(Project project) {
        projectEntityRepository.save(projectMapper.fromDomain(project));
    }

    @Override
    public void softDeleted(Project project) {
        ProjectEntity entity = projectMapper.fromDomain(project);
        entity.markDeleted();
        projectEntityRepository.save(entity);
    }
}
