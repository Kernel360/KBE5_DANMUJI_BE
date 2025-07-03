package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.ReadApprovalUseCase;
import com.back2basics.checklist.port.out.ApprovalQueryPort;
import com.back2basics.checklist.port.out.ChecklistQueryPort;
import com.back2basics.checklist.service.result.ApprovalResult;
import com.back2basics.checklist.service.result.ChecklistInfoResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadApprovalService implements ReadApprovalUseCase {

    private final ChecklistQueryPort checklistQueryPort;
    private final ApprovalQueryPort approvalQueryPort;

    @Override
    public ChecklistInfoResult findByRequestId(Long requestId) {
        Checklist request = checklistQueryPort.findById(requestId);
        return new ChecklistInfoResult(request.getId(), request.getProjectStepId(),
            request.getUserId(), request.getTitle(), request.getContent(),
            request.getChecklistStatus(), request.getRequestedAt());
    }

    @Override
    public List<ChecklistInfoResult> findAll() {
        return checklistQueryPort.findAll().stream()
            .map(request -> new ChecklistInfoResult(
                request.getId(),
                request.getProjectStepId(),
                request.getUserId(),
                request.getTitle(),
                request.getContent(),
                request.getChecklistStatus(),
                request.getCompletedAt()
            ))
            .toList();
    }

    @Override
    public List<ApprovalResult> findResponsesByRequestId(Long requestId) {
        List<Approval> responses = approvalQueryPort.findResponsesByRequestId(
            requestId);

        return responses.stream()
            .map(response -> new ApprovalResult(
                response.getId(),
                response.getApprovalRequestId(),
                response.getUserId(),
                response.getMessage(),
                response.getStatus(),
                response.getRespondedAt()
            ))
            .toList();
    }

    @Override
    public ApprovalResult findByResponseId(Long responseId) {
        Approval response = approvalQueryPort.findById(responseId);
        return new ApprovalResult(
            response.getId(),
            response.getApprovalRequestId(),
            response.getUserId(),
            response.getMessage(),
            response.getStatus(),
            response.getRespondedAt());
    }
}
