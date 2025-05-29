package com.back2basics.adapter.persistence.project.adapter.out;

import com.back2basics.adapter.persistence.project.entity.ProjectEntity;
import com.back2basics.adapter.persistence.project.mapper.ProjectMapper;
import com.back2basics.adapter.persistence.project.repository.ProjectEntityRepository;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.ProjectRepositoryPort;
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
        return projectEntityRepository.findById(id)
            .filter(it -> !it.isDeleted())
            .map(projectMapper::toDomain);
    }

    @Override
    public List<Project> findAll() {
        return projectEntityRepository.findAll()
            .stream()
            .filter(it -> !it.isDeleted())
            .map(projectMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void update(Project project) {
        projectEntityRepository.save(projectMapper.fromDomain(project));
    }
}
