package com.back2basics.assignment.service;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.assignment.port.in.DeleteAssignmentUseCase;
import com.back2basics.assignment.port.out.AssignmentQueryPort;
import com.back2basics.assignment.port.out.DeleteAssignmentPort;
import com.back2basics.infra.validator.CompanyValidator;
import com.back2basics.infra.validator.ProjectValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAssignmentService implements DeleteAssignmentUseCase {

    private final AssignmentQueryPort assignmentQueryPort;
    private final DeleteAssignmentPort deleteAssignmentPort;
    private final ProjectValidator projectValidator;
    private final CompanyValidator companyValidator;

    @Override
    public void deleteCompanies(Long projectId, Long companyId) {
        projectValidator.findById(projectId);
        companyValidator.validateCompanyExists(companyId);

        List<Assignment> assignments = assignmentQueryPort.findAllByProjectIdAndCompanyId(projectId,
            companyId);
        deleteAssignmentPort.deleteAll(assignments);
    }
}
