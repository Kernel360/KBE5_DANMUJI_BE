package com.back2basics.checklist.service;

import com.back2basics.checklist.model.ApprovalResponse;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.ReadApprovalUseCase;
import com.back2basics.checklist.port.out.ApprovalRequestQueryPort;
import com.back2basics.checklist.port.out.ApprovalResponseQueryPort;
import com.back2basics.checklist.service.result.ApprovalInfoResult;
import com.back2basics.checklist.service.result.ApproverResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadApprovalService implements ReadApprovalUseCase {

    private final ApprovalRequestQueryPort approvalRequestQueryPort;
    private final ApprovalResponseQueryPort approvalResponseQueryPort;

    @Override
    public ApprovalInfoResult findByRequestId(Long requestId) {
        Checklist request = approvalRequestQueryPort.findById(requestId);
        return new ApprovalInfoResult(request.getId(), request.getProjectStepId(),
            request.getUserId(), request.getTitle(), request.getContent(),
            request.getChecklistStatus(), request.getRequestedAt());
    }

    @Override
    public List<ApprovalInfoResult> findAll() {
        return approvalRequestQueryPort.findAll().stream()
            .map(request -> new ApprovalInfoResult(
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
    public List<ApproverResult> findResponsesByRequestId(Long requestId) {
        List<ApprovalResponse> responses = approvalResponseQueryPort.findResponsesByRequestId(
            requestId);

        return responses.stream()
            .map(response -> new ApproverResult(
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
    public ApproverResult findByResponseId(Long responseId) {
        ApprovalResponse response = approvalResponseQueryPort.findById(responseId);
        return new ApproverResult(
            response.getId(),
            response.getApprovalRequestId(),
            response.getUserId(),
            response.getMessage(),
            response.getStatus(),
            response.getRespondedAt());
    }
}
