package com.back2basics.adapter.persistence.projectuser.adapter;

import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.projectuser.ProjectUserEntity;
import com.back2basics.adapter.persistence.projectuser.ProjectUserEntityRepository;
import com.back2basics.adapter.persistence.projectuser.ProjectUserMapper;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.project.ProjectErrorCode;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.infra.exception.user.UserErrorCode;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.project.port.out.SaveProjectUserPort;
import com.back2basics.projectuser.model.ProjectUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveProjectUserAdapter implements SaveProjectUserPort {

    private final ProjectUserEntityRepository projectUserEntityRepository;
    private final ProjectEntityRepository projectEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final ProjectUserMapper mapper;

    @Override
    public void save(ProjectUser projectUser) {
        // todo: 할당을 여기서 하고 있는데 이게 맞을까 ....................
        ProjectUserEntity entity = mapper.toEntity(projectUser);
        ProjectEntity projectEntity = projectEntityRepository.findById(projectUser.getProjectId())
            .orElseThrow(() -> new ProjectException(ProjectErrorCode.PROJECT_NOT_FOUND));
        entity.assignProjectEntity(projectEntity);
        UserEntity userEntity = userEntityRepository.findById(projectUser.getUserId())
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        entity.assignUserEntity(userEntity);
        projectUserEntityRepository.save(entity);
    }
}
