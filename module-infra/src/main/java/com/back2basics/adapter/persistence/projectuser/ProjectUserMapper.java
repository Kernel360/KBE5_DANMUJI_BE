package com.back2basics.adapter.persistence.projectuser;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.projectuser.model.ProjectUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectUserMapper {

    public ProjectUser toDomain(ProjectUserEntity entity) {
        return ProjectUser.builder().id(entity.getId())
            .projectId(entity.getProject().getId())
            .userId(entity.getUser().getId())
            .companyType(entity.getCompanyType())
            .userType(entity.getUserType()).build();
    }

    public ProjectUserEntity toEntity(ProjectUser projectUser, ProjectEntity project,
        UserEntity user, CompanyEntity company) {
        return ProjectUserEntity.builder()
            .id(projectUser.getId())
            .project(project)
            .user(user)
            .company(company)
            .companyType(projectUser.getCompanyType())
            .userType(projectUser.getUserType())
            .build();
    }
}
