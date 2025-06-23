package com.back2basics.approval.port.out;

import com.back2basics.approval.model.ApprovalRequest;

public interface ApprovalRequestCommandPort {

    Long create(ApprovalRequest approvalRequest);

    void update(ApprovalRequest approvalRequest);

    void save(ApprovalRequest approvalRequest);
}
