package com.back2basics.adapter.persistence.project.adapter;

import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.ReadProjectPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadProjectAdapter implements ReadProjectPort {

    private final ProjectEntityRepository projectEntityRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Optional<Project> findById(Long id) {
        return projectEntityRepository.findByIdAndIsDeletedFalse(id)
            .map(projectMapper::toDomain);
    }

    @Override
    public List<Project> findAll() {
        return projectEntityRepository.findAllByIsDeletedFalse()
            .stream()
            .map(projectMapper::toDomain)
            .collect(Collectors.toList());
    }
}
