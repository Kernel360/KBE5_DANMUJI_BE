package com.back2basics.adapter.persistence.projectuser.adapter;

import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_USER_NOT_FOUND;

import com.back2basics.adapter.persistence.projectuser.ProjectUserEntityRepository;
import com.back2basics.adapter.persistence.projectuser.ProjectUserMapper;
import com.back2basics.company.model.CompanyType;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.projectuser.port.out.ProjectUserQueryPort;
import com.back2basics.user.model.UserType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectUserQueryAdapter implements ProjectUserQueryPort {

    private final ProjectUserEntityRepository projectUserEntityRepository;
    private final ProjectUserMapper projectUserMapper;

    @Override
    public List<ProjectUser> findUsersByProjectId(Long projectId) {

        return projectUserEntityRepository.findByProject_Id(projectId)
            .stream()
            .map(projectUserMapper::toDomain)
            .toList();
    }

    @Override
    public ProjectUser findByProjectIdAndUserTypeAndCompanyType(Long projectId, UserType userType, CompanyType companyType) {
        ProjectUser projectUser = projectUserMapper.toDomain(
            projectUserEntityRepository.findByProjectIdAndUserTypeAndCompanyType(projectId, userType, companyType)
        );
        return projectUser;
    }

    @Override
    public ProjectUser findByProjectIdAndUserId(Long projectId, Long userId) {
        ProjectUser projectUser = projectUserMapper.toDomain(
            projectUserEntityRepository.findByProjectIdAndUserId(projectId, userId)
        );
        return projectUser;
    }
}
