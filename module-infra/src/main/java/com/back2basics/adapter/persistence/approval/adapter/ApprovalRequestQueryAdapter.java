package com.back2basics.adapter.persistence.approval.adapter;

import static com.back2basics.infra.exception.approval.ApprovalErrorCode.APPROVAL_NOT_FOUND;

import com.back2basics.adapter.persistence.approval.mapper.ApprovalRequestMapper;
import com.back2basics.adapter.persistence.approval.repository.ApprovalRequestEntityRepository;
import com.back2basics.approval.model.ApprovalRequest;
import com.back2basics.approval.port.out.ApprovalRequestQueryPort;
import com.back2basics.infra.exception.approval.ApprovalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApprovalRequestQueryAdapter implements ApprovalRequestQueryPort {

    private final ApprovalRequestMapper mapper;
    private final ApprovalRequestEntityRepository approvalRequestEntityRepository;

    @Override
    public ApprovalRequest findById(Long requestId) {
        return approvalRequestEntityRepository.findById(requestId).map(mapper::toDomain)
            .orElseThrow(() -> new ApprovalException(APPROVAL_NOT_FOUND));
    }
}
