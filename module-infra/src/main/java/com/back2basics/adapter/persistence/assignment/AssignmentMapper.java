package com.back2basics.adapter.persistence.assignment;

import static com.back2basics.infra.exception.company.CompanyErrorCode.COMPANY_NOT_FOUND;
import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;
import static com.back2basics.infra.exception.user.UserErrorCode.USER_NOT_FOUND;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.company.CompanyMapper;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.company.CompanyException;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.project.model.Project;
import com.back2basics.assignment.model.Assignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentMapper {

    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;

    private final ProjectEntityRepository projectEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final CompanyEntityRepository companyEntityRepository;

    public Assignment toDomain(AssignmentEntity entity) {
        return Assignment.builder()
            .id(entity.getId())
            .project(Project.builder().id(entity.getProject().getId()).build())
            .user(userMapper.toDomain(entity.getUser()))
            .company(companyMapper.toDomain(entity.getCompany()))
            .userType(entity.getUserType())
            .companyType(entity.getCompanyType())
            .build();
    }

    public AssignmentEntity toEntity(Assignment assignment) {
        ProjectEntity project = projectEntityRepository.findById(assignment.getProject().getId())
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));

        UserEntity user = userEntityRepository.findById(assignment.getUser().getId())
            .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        CompanyEntity company = companyEntityRepository.findById(assignment.getUser().getCompanyId())
            .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));
        return AssignmentEntity.builder()
            .id(assignment.getId())
            .project(project)
            .user(user)
            .company(company)
            .userType(assignment.getUserType())
            .companyType(assignment.getCompanyType())
            .build();
    }
}
