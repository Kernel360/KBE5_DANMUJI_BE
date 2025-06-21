package com.back2basics.adapter.persistence.approval.adapter;

import static com.back2basics.infra.exception.approval.ApprovalErrorCode.APPROVAL_NOT_FOUND;

import com.back2basics.adapter.persistence.approval.mapper.ApprovalResponseMapper;
import com.back2basics.adapter.persistence.approval.repository.ApprovalResponseEntityRepository;
import com.back2basics.approval.model.ApprovalResponse;
import com.back2basics.approval.port.out.ApprovalResponseQueryPort;
import com.back2basics.infra.exception.approval.ApprovalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ApprovalResponseQueryAdapter implements ApprovalResponseQueryPort {

    private final ApprovalResponseMapper mapper;
    private final ApprovalResponseEntityRepository approvalResponseEntityRepository;

    @Override
    public boolean existsByIdAndUserId(Long responseId, Long approverId) {
        return approvalResponseEntityRepository.existsByIdAndApproverId(responseId,
            approverId);
    }

    @Override
    public ApprovalResponse findById(Long responseId) {
        return approvalResponseEntityRepository.findById(responseId)
            .map(mapper::toDomain)
            .orElseThrow(() -> new ApprovalException(APPROVAL_NOT_FOUND));
    }

    @Override
    public List<Long> findApproverIdsByRequestId(Long requestId) {
        return approvalResponseEntityRepository.findApproverIdByApprovalRequestId(requestId);
    }

}
