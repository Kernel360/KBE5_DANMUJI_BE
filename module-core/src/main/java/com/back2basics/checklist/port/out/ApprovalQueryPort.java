package com.back2basics.checklist.port.out;

import com.back2basics.checklist.model.Approval;
import java.util.List;

public interface ApprovalQueryPort {

    boolean existsByIdAndUserId(Long responseId, Long userId);

    Approval findById(Long responseId);

    List<Long> findApprovalIdsByChecklistId(Long stepId);

    List<Approval> findApprovalsByChecklistId(Long requestId);
}
