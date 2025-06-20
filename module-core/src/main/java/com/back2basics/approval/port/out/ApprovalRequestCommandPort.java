package com.back2basics.approval.port.out;

import com.back2basics.approval.model.ApprovalRequest;

public interface ApprovalRequestCommandPort {

    void save(ApprovalRequest approvalRequest);

}
