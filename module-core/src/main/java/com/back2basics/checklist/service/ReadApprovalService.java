package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.port.in.ReadApprovalUseCase;
import com.back2basics.checklist.port.out.ApprovalQueryPort;
import com.back2basics.checklist.service.result.ApprovalResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadApprovalService implements ReadApprovalUseCase {

    private final ApprovalQueryPort approvalQueryPort;

    @Override
    public List<ApprovalResult> findResponsesByRequestId(Long requestId) {
        List<Approval> responses = approvalQueryPort.findApprovalsByChecklistId(
            requestId);

        return responses.stream()
            .map(response -> new ApprovalResult(
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
    public ApprovalResult findByResponseId(Long responseId) {
        Approval response = approvalQueryPort.findById(responseId);
        return new ApprovalResult(
            response.getId(),
            response.getChecklistId(),
            response.getUserId(),
            response.getMessage(),
            response.getStatus(),
            response.getRespondedAt());
    }
}
