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

    private final ProjectStepMapper projectStepMapper;
    private final ProjectStepEntityRepository stepRepository;

    @Override
    public void save(ProjectStep projectStep) {
        ProjectStepEntity step = projectStepMapper.toEntity(projectStep);
        stepRepository.save(step);
    }
    @Override
    public void saveAll(List<ProjectStep> projectStepList) {
        List<ProjectStepEntity> entities = projectStepList.stream()
            .map(projectStepMapper::toEntity)
            .toList();
        stepRepository.saveAll(entities);
    }

}
