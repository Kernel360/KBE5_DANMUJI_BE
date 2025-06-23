package com.back2basics.infra.validation.validator;

import static com.back2basics.infra.exception.approval.ApprovalErrorCode.APPROVAL_NOT_FOUND;

import com.back2basics.approval.port.out.ApprovalResponseQueryPort;
import com.back2basics.infra.exception.approval.ApprovalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovalValidator {

    private final ApprovalResponseQueryPort approvalResponseQueryPort;

    public void validateApproval(Long responseId, Long userId) {

        boolean exists = approvalResponseQueryPort.existsByIdAndUserId(responseId, userId);
        if (!exists) {
            throw new ApprovalException(APPROVAL_NOT_FOUND);
        }
    }

}
