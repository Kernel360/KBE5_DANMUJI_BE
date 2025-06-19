package com.back2basics.adapter.persistence.projectstep.adapter;

import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class SaveProjectStepAdapter implements SaveProjectStepPort {

    private final ProjectStepEntityRepository stepRepository;
    private final ProjectMapper projectMapper;
    private final ReadProjectPort readProjectPort;
    private final ProjectStepMapper mapper;

    @Override
    public void defaultSave(ProjectStep projectStep) {
        ProjectEntity projectEntity = projectMapper.fromDomain(
            readProjectPort.findProjectById(projectStep.getProjectId()));
        ProjectStepEntity projectStepEntity = mapper.toEntity(projectStep, projectEntity);
        stepRepository.save(projectStepEntity);
    }

    //todo: 단계 추가 시 save - user not null -> 근데 update할 때도 이 save 사용하는데 이미 할당되어있는 project, user를 계속 찾아서 할당시키는 게 맞는지 .. 다른 save 만들어야 하나
    @Override
    public void save(ProjectStep projectStep) {
        ProjectEntity projectEntity = projectMapper.fromDomain(
            readProjectPort.findProjectById(projectStep.getProjectId()));
        ProjectStepEntity projectStepEntity = mapper.toEntity(projectStep, projectEntity);
        stepRepository.save(projectStepEntity);
    }

}
