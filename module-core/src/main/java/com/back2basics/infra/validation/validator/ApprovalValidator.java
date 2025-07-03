package com.back2basics.infra.validation.validator;

import static com.back2basics.infra.exception.approval.ApprovalErrorCode.APPROVAL_NOT_FOUND;

import com.back2basics.checklist.port.out.ApprovalQueryPort;
import com.back2basics.infra.exception.approval.ApprovalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalValidator {

    private final ApprovalQueryPort approvalQueryPort;

    public void validateApproval(Long responseId, Long userId) {

        boolean exists = approvalQueryPort.existsByIdAndUserId(responseId, userId);
        if (!exists) {
            throw new ApprovalException(APPROVAL_NOT_FOUND);
        }
    }

}
