package com.back2basics.adapter.persistence.assignment;

import static com.back2basics.infra.exception.company.CompanyErrorCode.COMPANY_NOT_FOUND;
import static com.back2basics.infra.exception.project.ProjectErrorCode.PROJECT_NOT_FOUND;
import static com.back2basics.infra.exception.user.UserErrorCode.USER_NOT_FOUND;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.assignment.model.Assignment;
import com.back2basics.infra.exception.company.CompanyException;
import com.back2basics.infra.exception.project.ProjectException;
import com.back2basics.infra.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentMapper {

    private final ProjectEntityRepository projectEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final CompanyEntityRepository companyEntityRepository;

    public Assignment toDomain(AssignmentEntity entity) {
        ProjectEntity project = projectEntityRepository.findById(entity.getProject().getId())
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));

        UserEntity user = userEntityRepository.findById(entity.getUser().getId())
            .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        CompanyEntity company = companyEntityRepository.findById(entity.getCompany().getId())
            .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        return Assignment.builder()
            .id(entity.getId())
            .projectId(project.getId())
            .userId(user.getId())
            .companyId(company.getId())
            .userType(entity.getUserType())
            .companyType(entity.getCompanyType())
            .name(user.getName())
            .companyName(company.getName())
            .position(user.getPosition())
            .build();
    }

    public AssignmentEntity toEntity(Assignment assignment) {
        ProjectEntity project = projectEntityRepository.findById(assignment.getProjectId())
            .orElseThrow(() -> new ProjectException(PROJECT_NOT_FOUND));

        UserEntity user = userEntityRepository.findById(assignment.getUserId())
            .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        CompanyEntity company = companyEntityRepository.findById(assignment.getCompanyId())
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
