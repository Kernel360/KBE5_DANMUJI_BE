package com.back2basics.adapter.persistence.assignment.adapter;

import com.back2basics.adapter.persistence.assignment.AssignmentEntityRepository;
import com.back2basics.adapter.persistence.assignment.AssignmentMapper;
import com.back2basics.assignment.model.Assignment;
import com.back2basics.assignment.port.out.AssignmentQueryPort;
import com.back2basics.company.model.CompanyType;
import com.back2basics.user.model.UserType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentQueryAdapter implements AssignmentQueryPort {

    private final AssignmentEntityRepository assignmentEntityRepository;
    private final AssignmentMapper assignmentMapper;

    @Override
    public List<Assignment> findAllByProjectId(Long projectId) {

        return assignmentEntityRepository.findByProject_Id(projectId)
            .stream()
            .map(assignmentMapper::toDomain)
            .toList();
    }

    @Override
    public List<Assignment> findAllByProjectIdAndCompanyId(Long projectId, Long companyId) {

        return assignmentEntityRepository.findByProject_IdAndCompany_Id(projectId, companyId)
            .stream()
            .map(assignmentMapper::toDomain)
            .toList();
    }

    @Override
    public CompanyType findCompanyTypeByProjectIdAndUserId(Long projectId, Long userId) {
        return assignmentEntityRepository.findCompanyTypeByProject_IdAndUser_Id(projectId, userId);
    }

    @Override
    public UserType findUserTypeByProjectIdAndUserId(Long projectId, Long userId) {
        return assignmentEntityRepository.findUserTypeByProject_IdAndUser_Id(projectId, userId);
    }
}
