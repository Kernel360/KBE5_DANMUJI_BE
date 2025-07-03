package com.back2basics.adapter.persistence.checklist.adapter;

import static com.back2basics.infra.exception.approval.ApprovalErrorCode.APPROVAL_NOT_FOUND;

import com.back2basics.adapter.persistence.checklist.mapper.ChecklistMapper;
import com.back2basics.adapter.persistence.checklist.repository.ChecklistEntityRepository;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.out.ChecklistQueryPort;
import com.back2basics.infra.exception.approval.ApprovalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChecklistQueryAdapter implements ChecklistQueryPort {

    private final ChecklistMapper mapper;
    private final ChecklistEntityRepository checklistEntityRepository;

    @Override
    public Checklist findById(Long requestId) {
        return checklistEntityRepository.findById(requestId).map(mapper::toDomain)
            .orElseThrow(() -> new ApprovalException(APPROVAL_NOT_FOUND));
    }

    @Override
    public List<Checklist> findAll() {
        return checklistEntityRepository.findAll().stream()
            .map(mapper::toDomain).toList();
    }
}
