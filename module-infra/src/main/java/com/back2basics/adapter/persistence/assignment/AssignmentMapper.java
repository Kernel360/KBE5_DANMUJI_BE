package com.back2basics.adapter.persistence.assignment;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyMapper;
import com.back2basics.adapter.persistence.project.ProjectEntity;
import com.back2basics.adapter.persistence.project.ProjectMapper;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.project.model.Project;
import com.back2basics.assignment.model.Assignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentMapper {

    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;

    public Assignment toDomain(AssignmentEntity entity) {
        return Assignment.builder()
            .id(entity.getId())
            .project(projectMapper.toDomain(entity.getProject()))
            .user(userMapper.toDomain(entity.getUser()))
            .company(companyMapper.toDomain(entity.getCompany()))
            .userType(entity.getUserType())
            .companyType(entity.getCompanyType())
            .build();
    }

    public Assignment toDomainTest(AssignmentEntity entity) {
        return Assignment.builder()
            .id(entity.getId())
            .project(Project.builder().id(entity.getProject().getId()).build())
            .user(userMapper.toDomain(entity.getUser()))
            .company(companyMapper.toDomain(entity.getCompany()))
            .userType(entity.getUserType())
            .companyType(entity.getCompanyType())
            .build();
    }


    // toEntity 할때 엔티티 자체를 받았네
    public AssignmentEntity toEntity(Assignment assignment, ProjectEntity project,
        UserEntity user, CompanyEntity company) {
        return com.back2basics.adapter.persistence.assignment.AssignmentEntity.builder()
            .id(assignment.getId())
            .project(project)
            .user(user)
            .company(company)
            .userType(assignment.getUserType())
            .companyType(assignment.getCompanyType())
            .build();
    }

    public AssignmentEntity toEntityTest(Assignment assignment) {
        return com.back2basics.adapter.persistence.assignment.AssignmentEntity.builder()
            .id(assignment.getId())
            .project(projectMapper.fromDomain(assignment.getProject()))
            .user(userMapper.toEntityTest(assignment.getUser()))
            .company(companyMapper.toEntity(assignment.getCompany()))
            .userType(assignment.getUserType())
            .companyType(assignment.getCompanyType())
            .build();
    }
}
