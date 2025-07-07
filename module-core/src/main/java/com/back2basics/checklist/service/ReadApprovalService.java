package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.port.in.ReadApprovalUseCase;
import com.back2basics.checklist.port.out.ApprovalQueryPort;
import com.back2basics.checklist.service.result.ChecklistWithApprovalResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadApprovalService implements ReadApprovalUseCase {

    private final ApprovalQueryPort approvalQueryPort;

    @Override
    public List<ChecklistWithApprovalResult> findAllByChecklistId(Long checklistId) {
        List<Approval> responses = approvalQueryPort.findApprovalsByChecklistId(
            checklistId);

        return responses.stream()
            .map(response -> new ChecklistWithApprovalResult(
                response.getId(),
                response.getChecklistId(),
                response.getUserId(),
                response.getMessage(),
                response.getStatus(),
                response.getRespondedAt()
            ))
            .toList();
    }

    @Override
    public ChecklistWithApprovalResult findById(Long id) {
        Approval response = approvalQueryPort.findById(id);
        return new ChecklistWithApprovalResult(
            response.getId(),
            response.getChecklistId(),
            response.getUserId(),
            response.getMessage(),
            response.getStatus(),
            response.getRespondedAt());
    }
}
