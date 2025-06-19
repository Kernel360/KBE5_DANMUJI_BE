package com.back2basics.adapter.persistence.projectstep.adapter;

import static com.back2basics.infra.exception.projectstep.ProjectStepErrorCode.STEP_NOT_FOUND;

import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.infra.exception.projectstep.ProjectStepException;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import java.util.List;
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
            .orElseThrow(() -> new ProjectStepException(STEP_NOT_FOUND));
    }

    @Override
    public List<ProjectStep> findAllByProjectId(Long projectId) {
        return repository.findAllByProjectIdAndDeletedAtIsNull(projectId)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Integer findMaxStepOrderByProjectId(Long projectId) {
        return repository.findMaxStepOrderByProjectId(projectId);
    }

}
