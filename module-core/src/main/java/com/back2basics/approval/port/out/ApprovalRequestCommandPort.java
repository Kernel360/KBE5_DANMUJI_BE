package com.back2basics.approval.port.out;

import com.back2basics.approval.model.ApprovalRequest;

public interface ApprovalRequestCommandPort {

    Long save(ApprovalRequest approvalRequest);

    void update(ApprovalRequest approvalRequest);
}
