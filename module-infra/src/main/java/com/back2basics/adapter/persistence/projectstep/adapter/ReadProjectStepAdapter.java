package com.back2basics.adapter.persistence.projectstep.adapter;

import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.infra.exception.project.ProjectErrorCode;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadProjectStepAdapter implements ReadProjectStepPort {
    private final ProjectStepEntityRepository repository;
    private final ProjectStepMapper mapper;

    @Override
    public ProjectStep findById(Long stepId) {
        return repository.findById(stepId).map(mapper::toDomain)
            .orElseThrow(()-> new ProjectException(ProjectErrorCode.PROJECT_NOT_FOUND));
    }

    @Override
    public List<ProjectStep> findAllByProjectId(Long projectId) {
        return repository.findAllByProjectId(projectId)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
