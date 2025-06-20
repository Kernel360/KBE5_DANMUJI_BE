package com.back2basics.adapter.persistence.projectstep.adapter;

import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;
import static com.back2basics.infra.exception.user.UserErrorCode.USER_NOT_FOUND;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveProjectStepAdapter implements SaveProjectStepPort {

    private final ProjectStepEntityRepository stepRepository;
    private final ProjectEntityRepository projectRepository;
    private final ReadProjectPort readProjectPort;
    private final ProjectStepMapper projectStepMapper;
    private final ProjectMapper projectMapper;

    // todo: 메서드 기능 동일함. 추후 통합
    @Override
    public void defaultSave(ProjectStep projectStep) {
        ProjectEntity project = projectRepository.findById(projectStep.getProjectId())
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));
        ProjectStepEntity step = projectStepMapper.toEntity(projectStep, project);
        stepRepository.save(step);
    }

    @Override
    public void save(ProjectStep projectStep) {
        ProjectEntity project = projectRepository.findById(projectStep.getProjectId())
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));
        ProjectStepEntity step = projectStepMapper.toEntity(projectStep, project);
        stepRepository.save(step);
    }
    @Override
    public void saveAll(List<ProjectStep> projectStepList) {
        List<ProjectStepEntity> entities = projectStepList.stream()
            .map(step -> {
                ProjectEntity projectEntity = projectMapper.toEntity(
                    readProjectPort.findProjectById(step.getProjectId()));
                return projectStepMapper.toEntity(step, projectEntity);
            })
            .toList();
        stepRepository.saveAll(entities);
    }

}
