package com.back2basics.approval.port.out;

import com.back2basics.approval.model.ApprovalRequest;

public interface ApprovalRequestQueryPort {

    ApprovalRequest findById(Long requestId);
}
