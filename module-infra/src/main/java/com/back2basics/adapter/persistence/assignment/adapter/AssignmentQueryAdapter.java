package com.back2basics.adapter.persistence.assignment.adapter;

import com.back2basics.adapter.persistence.assignment.AssignmentEntityRepository;
import com.back2basics.adapter.persistence.assignment.AssignmentMapper;
import com.back2basics.company.model.CompanyType;
import com.back2basics.assignment.model.Assignment;
import com.back2basics.assignment.port.out.AssignmentQueryPort;
import com.back2basics.user.model.UserType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentQueryAdapter implements AssignmentQueryPort {

    private final AssignmentEntityRepository projectUserEntityRepository;
    private final AssignmentMapper projectUserMapper;

    @Override
    public List<Assignment> findUsersByProjectId(Long projectId) {

        return projectUserEntityRepository.findByProject_Id(projectId)
            .stream()
            .map(projectUserMapper::toDomain)
            .toList();
    }

    @Override
    public Assignment findByProjectIdAndUserTypeAndCompanyType(Long projectId, UserType userType, CompanyType companyType) {
        Assignment assignment = projectUserMapper.toDomain(
            projectUserEntityRepository.findByProjectIdAndUserTypeAndCompanyType(projectId, userType, companyType)
        );
        return assignment;
    }

    @Override
    public Assignment findByProjectIdAndUserId(Long projectId, Long userId) {
        Assignment assignment = projectUserMapper.toDomain(
            projectUserEntityRepository.findByProjectIdAndUserId(projectId, userId)
        );
        return assignment;
    }
}
