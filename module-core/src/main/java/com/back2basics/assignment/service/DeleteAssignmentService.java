package com.back2basics.assignment.service;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.assignment.port.in.DeleteAssignmentUseCase;
import com.back2basics.assignment.port.out.AssignmentQueryPort;
import com.back2basics.assignment.port.out.DeleteAssignmentPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAssignmentService implements DeleteAssignmentUseCase {

    private final AssignmentQueryPort assignmentQueryPort;
    private final DeleteAssignmentPort deleteAssignmentPort;

    @Override
    public void deleteCompany(Long projectId, Long companyId) {
        List<Assignment> assignments = assignmentQueryPort.findByProjectIdAndCompanyId(projectId, companyId);
        deleteAssignmentPort.DeleteAllInBatch(assignments);
    }
}
