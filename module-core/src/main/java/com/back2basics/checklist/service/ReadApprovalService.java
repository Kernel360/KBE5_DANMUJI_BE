package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.ReadApprovalUseCase;
import com.back2basics.checklist.port.out.ApprovalQueryPort;
import com.back2basics.checklist.port.out.ChecklistQueryPort;
import com.back2basics.checklist.service.result.ApprovalResult;
import com.back2basics.checklist.service.result.ChecklistDetailResult;
import com.back2basics.user.port.out.UserQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadApprovalService implements ReadApprovalUseCase {

    private final UserQueryPort userQueryPort;
    private final ChecklistQueryPort checklistQueryPort;
    private final ApprovalQueryPort approvalQueryPort;

    public ChecklistDetailResult findAllByChecklistId(Long checklistId) {
        List<Approval> approvals = approvalQueryPort.findApprovalsByChecklistId(checklistId);

        List<ApprovalResult> approvalResults = approvals.stream()
            .map(approval -> new ApprovalResult(
                approval.getId(),
                approval.getChecklistId(),
                approval.getUserId(),
                userQueryPort.findById(approval.getUserId()).getName(),
                userQueryPort.findById(approval.getUserId()).getUsername(),
                approval.getMessage(),
                approval.getStatus(),
                approval.getCreatedAt(),
                approval.getRespondedAt()
            ))
            .toList();

        Checklist checklist = checklistQueryPort.findById(checklistId);

        return new ChecklistDetailResult(
            checklist.getId(),
            checklist.getProjectStepId(),
            checklist.getUserId(),
            userQueryPort.findById(checklist.getUserId()).getName(),
            userQueryPort.findById(checklist.getUserId()).getUsername(),
            checklist.getChecklistStatus(),
            checklist.getTitle(),
            checklist.getContent(),
            checklist.getCreatedAt(),
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
            userQueryPort.findById(response.getUserId()).getName(),
            userQueryPort.findById(response.getUserId()).getUsername(),
            response.getMessage(),
            response.getStatus(),
            response.getCreatedAt(),
            response.getRespondedAt());
    }
}
