package com.back2basics.checklist.port.out;

import com.back2basics.checklist.model.ApprovalResponse;

public interface ApprovalResponseCommandPort {

    void update(ApprovalResponse approvalResponse);
}
