package com.back2basics.adapter.persistence.checklist.adapter;

import static com.back2basics.infra.exception.approval.ApprovalErrorCode.APPROVAL_NOT_FOUND;

import com.back2basics.adapter.persistence.checklist.mapper.ApprovalMapper;
import com.back2basics.adapter.persistence.checklist.repository.ApprovalEntityRepository;
import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.port.out.ApprovalQueryPort;
import com.back2basics.infra.exception.approval.ApprovalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ApprovalQueryAdapter implements ApprovalQueryPort {

    private final ApprovalMapper mapper;
    private final ApprovalEntityRepository approvalEntityRepository;

    @Override
    public boolean existsByIdAndUserId(Long responseId, Long userId) {
        return approvalEntityRepository.existsByIdAndUserId(responseId,
            userId);
    }

    @Override
    public Approval findById(Long id) {
        return approvalEntityRepository.findById(id)
            .map(mapper::toDomain)
            .orElseThrow(() -> new ApprovalException(APPROVAL_NOT_FOUND));
    }

    @Override
    public List<Long> findApproverIdsByRequestId(Long checklistId) {
        return approvalEntityRepository.findIdsByChecklistId(checklistId);
    }

    @Override
    public List<Approval> findResponsesByRequestId(Long checklistId) {
        return approvalEntityRepository.findAllByChecklistId(checklistId).stream()
            .map(mapper::toDomain)
            .toList();
    }

}
