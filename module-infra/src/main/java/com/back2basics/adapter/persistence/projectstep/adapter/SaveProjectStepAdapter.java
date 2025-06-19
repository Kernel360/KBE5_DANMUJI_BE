package com.back2basics.adapter.persistence.projectstep.adapter;

import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepMapper;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.project.ProjectErrorCode;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveProjectStepAdapter implements SaveProjectStepPort {

    private final ProjectValidator projectValidator;
    private final ProjectStepEntityRepository stepRepository;
    private final ProjectEntityRepository projectRepository;
    private final UserEntityRepository userRepository;
    private final ProjectStepMapper mapper;

    // todo: 여기서 연관관계 바로 할당해도 되는지? - pr에 작성.

    //todo: project 생성 시 디폴트 save - user(승인자) null
    // todo: 확인
    @Override
    public void defaultSave(ProjectStep projectStep) {
        // 1. project 연관 안들어간 step entity
        ProjectStepEntity projectStepEntity = mapper.toEntity(projectStep);
        // 2. project Entity 찾기
        ProjectEntity projectEntity = projectRepository.findById(projectStep.getProjectId())
            .orElseThrow(() -> new ProjectException(ProjectErrorCode.PROJECT_NOT_FOUND));
        // 3. project 연관
        stepRepository.save(projectStepEntity);
    }

    //todo: 단계 추가 시 save - user not null -> 근데 update할 때도 이 save 사용하는데 이미 할당되어있는 project, user를 계속 찾아서 할당시키는 게 맞는지 .. 다른 save 만들어야 하나
    @Override
    public void save(ProjectStep projectStep) {
        stepRepository.save(mapper.toEntity(projectStep));
    }

    @Override
    public void update(ProjectStep projectStep) {

        stepRepository.save(mapper.toEntity(projectStep));

    }

}
