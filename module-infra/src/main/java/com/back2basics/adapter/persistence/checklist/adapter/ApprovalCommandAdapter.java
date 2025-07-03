package com.back2basics.adapter.persistence.checklist.adapter;

import com.back2basics.adapter.persistence.checklist.entity.ApprovalEntity;
import com.back2basics.adapter.persistence.checklist.repository.ApprovalEntityRepository;
import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.port.out.ApprovalCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ApprovalCommandAdapter implements ApprovalCommandPort {

    private final ApprovalEntityRepository repository;

    @Override
    public void update(Approval response) {
        ApprovalEntity entity = repository.getReferenceById(response.getId());

        repository.save(entity);
    }
}
