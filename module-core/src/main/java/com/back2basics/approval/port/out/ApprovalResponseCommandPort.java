package com.back2basics.approval.port.out;

import com.back2basics.approval.model.ApprovalResponse;

public interface ApprovalResponseCommandPort {

    void update(ApprovalResponse approvalResponse);
}
