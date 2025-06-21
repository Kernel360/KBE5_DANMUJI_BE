package com.back2basics.approval.service;

import com.back2basics.approval.model.ApprovalRequest;
import com.back2basics.approval.port.in.CreateApprovalRequestUseCase;
import com.back2basics.approval.port.in.command.CreateApprovalCommand;
import com.back2basics.approval.port.out.ApprovalRequestCommandPort;
import com.back2basics.infra.validation.validator.ProjectStepValidator;
import com.back2basics.infra.validation.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateApprovalRequestRequestService implements CreateApprovalRequestUseCase {

    private final UserValidator userValidator; // validation 처리는 service에서 처리해야 함
    private final ProjectStepValidator projectStepValidator;
    private final ApprovalRequestCommandPort approvalRequestCommandPort;

    @Override
    public void create(Long stepId, Long requesterId, CreateApprovalCommand command) {
        projectStepValidator.validateNotFoundStepId(stepId);
        userValidator.validateNotFoundUserId(requesterId);
        userValidator.validateAllUsersExist(command.responseIds());
        ApprovalRequest approvalRequest = ApprovalRequest.create(stepId, requesterId,
            command.responseIds());
        approvalRequestCommandPort.save(approvalRequest);
    }
}
