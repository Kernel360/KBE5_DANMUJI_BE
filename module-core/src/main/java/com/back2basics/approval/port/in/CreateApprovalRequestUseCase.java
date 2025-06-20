package com.back2basics.approval.port.in;

import java.util.List;

public interface CreateApprovalRequestUseCase {

    void create(Long stepId, Long requesterId, List<Long> responseIds);
}
