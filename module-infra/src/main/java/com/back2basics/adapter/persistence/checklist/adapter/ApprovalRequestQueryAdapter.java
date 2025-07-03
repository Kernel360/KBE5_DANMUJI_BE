package com.back2basics.adapter.persistence.checklist.adapter;

import static com.back2basics.infra.exception.approval.ApprovalErrorCode.APPROVAL_NOT_FOUND;

import com.back2basics.adapter.persistence.checklist.mapper.ApprovalRequestMapper;
import com.back2basics.adapter.persistence.checklist.repository.ApprovalRequestEntityRepository;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.out.ApprovalRequestQueryPort;
import com.back2basics.infra.exception.approval.ApprovalException;
import java.util.List;
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
    public Checklist findById(Long requestId) {
        return approvalRequestEntityRepository.findById(requestId).map(mapper::toDomain)
            .orElseThrow(() -> new ApprovalException(APPROVAL_NOT_FOUND));
    }

    @Override
    public List<Checklist> findAll() {
        return approvalRequestEntityRepository.findAll().stream()
            .map(mapper::toDomain).toList();
    }
}
