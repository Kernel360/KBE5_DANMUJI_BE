package com.back2basics.approval.port.out;

import com.back2basics.approval.model.ApprovalResponse;

public interface ApprovalResponseQueryPort {

    boolean existsByIdAndUserId(Long responseId, Long userId);

    ApprovalResponse findById(Long responseId);
}
