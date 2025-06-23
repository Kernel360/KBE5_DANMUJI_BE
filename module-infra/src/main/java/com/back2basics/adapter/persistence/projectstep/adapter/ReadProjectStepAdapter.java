package com.back2basics.adapter.persistence.projectstep.adapter;

import static com.back2basics.infra.exception.projectstep.ProjectStepErrorCode.STEP_NOT_FOUND;

import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.infra.exception.projectstep.ProjectStepException;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadProjectStepAdapter implements ReadProjectStepPort {

    private final ProjectStepEntityRepository projectStepEntityRepository;
    private final ProjectValidator projectValidator;
    private final ProjectStepMapper projectStepMapper;

    @Override
    public ProjectStep findById(Long stepId) {
        return projectStepEntityRepository.findById(stepId).map(projectStepMapper::toDomain)
            .orElseThrow(() -> new ProjectStepException(STEP_NOT_FOUND));
    }

    @Override
    public List<ProjectStep> findAllByProjectId(Long projectId) {
        Project project = projectValidator.findProjectById(projectId);

        List<ProjectStepEntity> stepEntities = projectStepEntityRepository.findAllByProjectIdAndDeletedAtIsNull(
            project.getId());

        if (stepEntities.isEmpty()) {
            throw new ProjectStepException(STEP_NOT_FOUND);
        }
        return stepEntities.stream().map(projectStepMapper::toDomain).toList();
    }

    @Override
    public List<ProjectStep> findAllById(List<Long> ids) {
        List<ProjectStepEntity> entities = projectStepEntityRepository.findAllById(ids);
        return entities.stream()
            .map(projectStepMapper::toDomain)
            .toList();
    }

    @Override
    public Integer findMaxStepOrderByProjectId(Long projectId) {
        return projectStepEntityRepository.findMaxStepOrderByProjectId(projectId);
    }

    @Override

    public int totalCompletedStep(Long projectId) {
        return projectStepEntityRepository.countByProject_IdAndProjectStepStatus(projectId,
            ProjectStepStatus.COMPLETED);
    }

    @Override
    public int totalStep(Long projectId) {
        return projectStepEntityRepository.countByProject_Id(projectId);
    }

    public boolean existsById(Long stepId) {
        return projectStepEntityRepository.existsById(stepId);
    }
}
