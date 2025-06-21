package com.back2basics.approval.port.out;

import com.back2basics.approval.model.ApprovalResponse;
import java.util.List;

public interface ApprovalResponseQueryPort {

    boolean existsByIdAndUserId(Long responseId, Long userId);

    ApprovalResponse findById(Long responseId);

    List<Long> findApproverIdsByRequestId(Long stepId);

    List<ApprovalResponse> findResponsesByRequestId(Long requestId);
}
