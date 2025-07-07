package com.back2basics.checklist.port.in;

public interface DeleteApprovalUseCase {

    void delete(Long userId, Long approvalId);
}
