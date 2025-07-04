package com.back2basics.approval.service;

import com.back2basics.approval.model.ApprovalRequest;
import com.back2basics.approval.port.in.CreateApprovalRequestUseCase;
import com.back2basics.approval.port.in.command.CreateApprovalCommand;
import com.back2basics.approval.port.out.ApprovalRequestCommandPort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validator.ProjectStepValidator;
import com.back2basics.infra.validator.UserValidator;
import com.back2basics.notify.port.in.NotifyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateApprovalRequestService implements CreateApprovalRequestUseCase {

    private final UserValidator userValidator; // validation 처리는 service에서 처리해야 함
    private final ProjectStepValidator projectStepValidator;
    private final ApprovalRequestCommandPort approvalRequestCommandPort;
    private final NotifyUseCase notifyUseCase;
    private final HistoryLogService historyLogService;

    @Override
    public void create(Long stepId, Long requesterId, CreateApprovalCommand command) {
        projectStepValidator.validateNotFoundStepId(stepId);
        userValidator.validateNotFoundUserId(requesterId);
        userValidator.validateAllUsersExist(command.responseIds());

        ApprovalRequest approvalRequest = ApprovalRequest.create(stepId, requesterId,
            command.responseIds());
        ApprovalRequest savedApprovalRequest = approvalRequestCommandPort.create(approvalRequest);

        // todo
//        for (Long clientId : command.responseIds()) {
//            SendNotificationCommand notifyCommand = new SendNotificationCommand(
//                clientId,
//                savedApprovalRequest.getId(),
//                NotificationType.STEP_APPROVAL_REQUEST.getDescription(),
//                NotificationType.STEP_APPROVAL_REQUEST
//            );
//            notifyUseCase.notify(notifyCommand);
//        }

        historyLogService.logCreated(DomainType.APPROVAL_REQUEST, requesterId, savedApprovalRequest,
            "승인 요청 생성");
    }
}
