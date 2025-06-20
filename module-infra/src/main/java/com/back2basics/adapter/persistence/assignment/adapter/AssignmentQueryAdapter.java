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

    private final AssignmentEntityRepository assignmentEntityRepository;
    private final AssignmentMapper assignmentMapper;


    // todo: 변수명 변경
    @Override
    public List<Assignment> findUsersByProjectId(Long projectId) {

        return assignmentEntityRepository.findByProject_Id(projectId)
            .stream()
            .map(assignmentMapper::toDomain)
            .toList();
    }

    @Override
    public Assignment findByProjectIdAndUserTypeAndCompanyType(Long projectId, UserType userType,
        CompanyType companyType) {
        return assignmentMapper.toDomain(
            assignmentEntityRepository.findByProjectIdAndUserTypeAndCompanyType(projectId, userType,
                companyType)
        );
    }

    @Override
    public List<Assignment> findByProjectIdAndUserId(Long projectId, Long userId) {
        return assignmentEntityRepository.findByProject_IdAndUser_Id(projectId, userId)
            .stream().map(assignmentMapper::toDomainTest).toList();
    }

    @Override
    public List<Assignment> findByProjectIdAndCompanyId(Long projectId, Long companyId) {

        return assignmentEntityRepository.findByProject_IdAndCompany_Id(projectId, companyId)
            .stream()
            .map(assignmentMapper::toDomainTest)
            .toList(); // toDomain, toDomainTest 차이를 잘 모르겠음 내가 만든건데 내가 이해못함.
    }
}
