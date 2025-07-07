package com.back2basics.checklist.port.out;

import com.back2basics.checklist.model.Approval;

public interface ApprovalCommandPort {

    void update(Approval approval);

    void delete(Approval approval);
}
