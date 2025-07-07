package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.ReadApprovalUseCase;
import com.back2basics.checklist.port.out.ApprovalQueryPort;
import com.back2basics.checklist.port.out.ChecklistQueryPort;
import com.back2basics.checklist.service.result.ApprovalResult;
import com.back2basics.checklist.service.result.ChecklistDetailResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadApprovalService implements ReadApprovalUseCase {

    private final ChecklistQueryPort checklistQueryPort;
    private final ApprovalQueryPort approvalQueryPort;

    public ChecklistDetailResult findAllByChecklistId(Long checklistId) {
        List<Approval> approvals = approvalQueryPort.findApprovalsByChecklistId(checklistId);

        List<ApprovalResult> approvalResults = approvals.stream()
            .map(approval -> new ApprovalResult(
                approval.getId(),
                approval.getChecklistId(),
                approval.getUserId(),
                approval.getMessage(),
                approval.getStatus(),
                approval.getRespondedAt()
            ))
            .toList();

        Checklist checklist = checklistQueryPort.findById(checklistId);

        return new ChecklistDetailResult(
            checklist.getId(),
            checklist.getProjectStepId(),
            checklist.getUserId(),
            checklist.getChecklistStatus(),
            checklist.getCompletedAt(),
            approvalResults
        );
    }

    @Override
    public ApprovalResult findById(Long id) {
        Approval response = approvalQueryPort.findById(id);
        return new ApprovalResult(
            response.getId(),
            response.getChecklistId(),
            response.getUserId(),
            response.getMessage(),
            response.getStatus(),
            response.getRespondedAt());
    }
}
