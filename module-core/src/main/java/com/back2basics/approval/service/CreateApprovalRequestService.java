package com.back2basics.approval.service;

import com.back2basics.approval.model.ApprovalRequest;
import com.back2basics.approval.port.in.CreateApprovalRequestUseCase;
import com.back2basics.approval.port.out.ApprovalRequestCommandPort;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateApprovalRequestService implements CreateApprovalRequestUseCase {

    private final ApprovalRequestCommandPort approvalRequestCommandPort;
    private final ReadProjectStepPort readProjectStepPort;
    private final UserValidator userValidator; // validation 처리는 service에서 처리해야 함

    @Override
    public void create(Long stepId, Long requesterId, List<Long> responseIds) {
        ProjectStep projectStep = readProjectStepPort.findById(stepId); // validator 추가
        userValidator.validateNotFoundUserId(requesterId);
        userValidator.validateAllUsersExist(responseIds);
        ApprovalRequest approvalRequest = ApprovalRequest.create(stepId, requesterId, responseIds);
        approvalRequestCommandPort.save(approvalRequest);
    }
}
