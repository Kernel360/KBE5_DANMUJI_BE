package com.back2basics.adapter.persistence.project.adapter;

import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.adapter.persistence.project.mapper.ProjectMapper;
import com.back2basics.adapter.persistence.project.repository.ProjectEntityRepository;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.out.ReadProjectPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
