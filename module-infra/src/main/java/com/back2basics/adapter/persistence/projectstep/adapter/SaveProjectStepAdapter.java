package com.back2basics.adapter.persistence.projectstep.adapter;

import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveProjectStepAdapter implements SaveProjectStepPort {

    private final ProjectStepMapper projectStepMapper;
    private final ProjectStepEntityRepository stepRepository;

    @Override
    public void defaultSave(ProjectStep projectStep) {

    }

    @Override
    public ProjectStep save(ProjectStep projectStep) {
        ProjectStepEntity step = projectStepMapper.toEntity(projectStep);
        ProjectStepEntity savedStepEntity = stepRepository.save(step);

        return projectStepMapper.toDomain(savedStepEntity);
    }

    @Override
    public void saveAll(List<ProjectStep> projectStepList) {
        List<ProjectStepEntity> entities = projectStepList.stream()
            .map(projectStepMapper::toEntity)
            .toList();
        stepRepository.saveAll(entities);
    }

}
