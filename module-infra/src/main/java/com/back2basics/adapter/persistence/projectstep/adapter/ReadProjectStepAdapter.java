package com.back2basics.adapter.persistence.projectstep.adapter;

import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
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
    public List<ProjectStep> findAllByProjectId(Long projectId) {
        return repository.findAllByProjectId(projectId)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

    }
}
