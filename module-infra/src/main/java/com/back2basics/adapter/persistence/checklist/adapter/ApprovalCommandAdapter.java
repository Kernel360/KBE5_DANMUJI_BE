package com.back2basics.adapter.persistence.checklist.adapter;

import com.back2basics.adapter.persistence.checklist.entity.ApprovalEntity;
import com.back2basics.adapter.persistence.checklist.entity.ChecklistEntity;
import com.back2basics.adapter.persistence.checklist.mapper.ApprovalMapper;
import com.back2basics.adapter.persistence.checklist.repository.ApprovalEntityRepository;
import com.back2basics.adapter.persistence.checklist.repository.ChecklistEntityRepository;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.checklist.model.Approval;
import com.back2basics.checklist.port.out.ApprovalCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ApprovalCommandAdapter implements ApprovalCommandPort {

    private final ApprovalMapper mapper;
    private final ApprovalEntityRepository repository;
    private final ChecklistEntityRepository checklistEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Override
    public void update(Approval approval) {
        ChecklistEntity checklist = checklistEntityRepository.getReferenceById(
            approval.getChecklistId());
        UserEntity user = userEntityRepository.getReferenceById(approval.getUserId());
        repository.save(mapper.toEntity(approval, checklist, user));
    }

    @Override
    public void delete(Approval approval) {
        ChecklistEntity checklist = checklistEntityRepository.getReferenceById(
            approval.getChecklistId());
        UserEntity user = userEntityRepository.getReferenceById(approval.getUserId());
        ApprovalEntity approvalEntity = mapper.toEntity(approval, checklist, user);
        approvalEntity.markDeleted();
    }
}
