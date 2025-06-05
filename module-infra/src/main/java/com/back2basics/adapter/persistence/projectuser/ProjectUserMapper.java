package com.back2basics.adapter.persistence.projectuser;

import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectUserMapper {

    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;

    public ProjectUser toDomain(ProjectUserEntity entity) {
        return ProjectUser.builder().id(entity.getId())
            .projectId(entity.getProject().getId())
            .userId(entity.getUser().getId())
            .companyType(entity.getCompanyType())
            .userType(entity.getUserType()).build();
    }

    public ProjectUserEntity toEntity(ProjectUser projectUser) {
        return ProjectUserEntity.builder()
            .companyType(projectUser.getCompanyType())
            .userType(projectUser.getUserType())
            .build();
    }
}
