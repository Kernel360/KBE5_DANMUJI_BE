package com.back2basics.adapter.persistence.checklist.adapter;

import static com.back2basics.infra.exception.approval.ApprovalErrorCode.APPROVAL_NOT_FOUND;

import com.back2basics.adapter.persistence.checklist.entity.ApprovalResponseEntity;
import com.back2basics.adapter.persistence.checklist.repository.ApprovalResponseEntityRepository;
import com.back2basics.checklist.model.ApprovalResponse;
import com.back2basics.checklist.port.out.ApprovalResponseCommandPort;
import com.back2basics.infra.exception.approval.ApprovalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ApprovalResponseCommandAdapter implements ApprovalResponseCommandPort {

    private final ApprovalResponseEntityRepository repository;

    @Override
    public void update(ApprovalResponse response) {
        ApprovalResponseEntity entity = repository.findById(response.getId())
            .orElseThrow(() -> new ApprovalException(APPROVAL_NOT_FOUND));

        entity.updateFromDomain(response);
    }
}
