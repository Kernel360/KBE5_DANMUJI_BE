package com.back2basics.adapter.persistence.projectuser.adapter;

import static com.back2basics.infra.exception.company.CompanyErrorCode.COMPANY_NOT_FOUND;
import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;
import static com.back2basics.infra.exception.user.UserErrorCode.USER_NOT_FOUND;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.projectuser.ProjectUserEntity;
import com.back2basics.adapter.persistence.projectuser.ProjectUserEntityRepository;
import com.back2basics.adapter.persistence.projectuser.ProjectUserMapper;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.company.CompanyException;
import com.back2basics.infra.exception.project.ProjectException;
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
    private final CompanyEntityRepository companyEntityRepository;
    private final ProjectUserMapper mapper;

    @Override
    public void save(ProjectUser projectUser) {
        ProjectEntity project = projectEntityRepository.findById(projectUser.getProject().getId())
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));

        UserEntity user = userEntityRepository.findById(projectUser.getUser().getId())
            .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        CompanyEntity company = companyEntityRepository.findById(projectUser.getCompany().getId())
            .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        ProjectUserEntity entity = mapper.toEntity(projectUser, project, user, company);
        projectUserEntityRepository.save(entity);
    }
}
