package com.back2basics.approval.port.out;

import com.back2basics.approval.model.ApprovalRequest;
import java.util.List;

public interface ApprovalRequestQueryPort {

    ApprovalRequest findById(Long requestId);

    List<ApprovalRequest> findAll();
}
