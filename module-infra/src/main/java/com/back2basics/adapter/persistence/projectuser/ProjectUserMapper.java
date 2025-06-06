package com.back2basics.adapter.persistence.projectuser;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyMapper;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.projectuser.model.ProjectUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectUserMapper {

    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;

    public ProjectUser toDomain(ProjectUserEntity entity) {
        return ProjectUser.builder()
            .id(entity.getId())
            .project(projectMapper.toDomain(entity.getProject()))
            .user(userMapper.toDomain(entity.getUser()))
            .company(companyMapper.toDomain(entity.getCompany()))
            .userType(entity.getUserType())
            .companyType(entity.getCompanyType())
            .build();
    }

    public ProjectUserEntity toEntity(ProjectUser projectUser, ProjectEntity project,
        UserEntity user, CompanyEntity company) {
        return ProjectUserEntity.builder()
            .id(projectUser.getId())
            .project(project)
            .user(user)
            .company(company)
            .userType(projectUser.getUserType())
            .companyType(projectUser.getCompanyType())
            .build();
    }
}
