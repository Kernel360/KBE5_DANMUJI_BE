package com.back2basics.adapter.persistence.checklist.adapter;

import com.back2basics.adapter.persistence.checklist.entity.ApprovalEntity;
import com.back2basics.adapter.persistence.checklist.entity.ChecklistEntity;
import com.back2basics.adapter.persistence.checklist.mapper.ChecklistMapper;
import com.back2basics.adapter.persistence.checklist.repository.ApprovalEntityRepository;
import com.back2basics.adapter.persistence.checklist.repository.ChecklistEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.model.ChecklistStatus;
import com.back2basics.checklist.port.out.ChecklistCommandPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ChecklistCommandAdapter implements ChecklistCommandPort {

    private final ChecklistMapper mapper;
    private final ChecklistEntityRepository checklistEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final ProjectStepEntityRepository projectStepEntityRepository;
    private final ApprovalEntityRepository approvalEntityRepository;

    @Override
    public Checklist create(Checklist checklist) {

        // 실제 쿼리를 즉시 실행하지 않고, 해당 ID에 대한 지연 프록시 객체 반환
        // 실제 값이 필요할 때까지 DB에 접근하지 않음
        UserEntity userEntity = userEntityRepository.getReferenceById(
            checklist.getUserId());

        ProjectStepEntity projectStepEntity = projectStepEntityRepository.getReferenceById(
            checklist.getProjectStepId());

        ChecklistEntity entity = new ChecklistEntity(projectStepEntity, userEntity,
            checklist.getTitle(), checklist.getContent(), ChecklistStatus.PENDING);

        List<ApprovalEntity> responses = checklist.getResponseIds().stream()
            .map(responseId -> {
                UserEntity response = userEntityRepository.getReferenceById(responseId);
                return new ApprovalEntity(entity, response);
            })
            .toList();

        entity.addResponses(responses);
        checklistEntityRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public void update(Checklist checklist) {
        ChecklistEntity entity = checklistEntityRepository.getReferenceById(
            checklist.getId());

        List<Long> existingIds = entity.getApproval().stream()
            .map(r -> r.getUser().getId())
            .toList();

        List<ApprovalEntity> newResponses = checklist.getResponseIds().stream()
            .filter(id -> !existingIds.contains(id))
            .map(responseId -> {
                UserEntity user = userEntityRepository.getReferenceById(responseId);
                return new ApprovalEntity(entity, user);
            })
            .toList();

        entity.addResponses(newResponses);
        checklistEntityRepository.save(entity);

    }

    @Override
    public void save(Checklist checklist) {
        ProjectStepEntity projectStepEntity = projectStepEntityRepository.getReferenceById(
            checklist.getProjectStepId());
        UserEntity requester = userEntityRepository.getReferenceById(
            checklist.getUserId());
        List<ApprovalEntity> approvalResponseEntities = approvalEntityRepository.findAllByChecklistId(
            checklist.getId());

        checklistEntityRepository.save(
            mapper.toEntity(checklist, projectStepEntity, requester,
                approvalResponseEntities));
    }

}
