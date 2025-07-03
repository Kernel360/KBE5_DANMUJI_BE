package com.back2basics.adapter.persistence.checklist.adapter;

import com.back2basics.adapter.persistence.checklist.entity.ApprovalResponseEntity;
import com.back2basics.adapter.persistence.checklist.entity.ChecklistEntity;
import com.back2basics.adapter.persistence.checklist.mapper.ApprovalRequestMapper;
import com.back2basics.adapter.persistence.checklist.repository.ApprovalRequestEntityRepository;
import com.back2basics.adapter.persistence.checklist.repository.ApprovalResponseEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.model.ChecklistStatus;
import com.back2basics.checklist.port.out.ApprovalRequestCommandPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ApprovalRequestCommandAdapter implements ApprovalRequestCommandPort {

    private final ApprovalRequestMapper mapper;
    private final ApprovalRequestEntityRepository approvalRequestEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final ProjectStepEntityRepository projectStepEntityRepository;
    private final ApprovalResponseEntityRepository approvalResponseEntityRepository;

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

        List<ApprovalResponseEntity> responses = checklist.getResponseIds().stream()
            .map(responseId -> {
                UserEntity response = userEntityRepository.getReferenceById(responseId);
                return new ApprovalResponseEntity(entity, response);
            })
            .toList();

        entity.addResponses(responses);
        approvalRequestEntityRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public void update(Checklist checklist) {
        ChecklistEntity entity = approvalRequestEntityRepository.getReferenceById(
            checklist.getId());

        List<Long> existingIds = entity.getResponses().stream()
            .map(r -> r.getApprover().getId())
            .toList();

        List<ApprovalResponseEntity> newResponses = checklist.getResponseIds().stream()
            .filter(id -> !existingIds.contains(id))
            .map(responseId -> {
                UserEntity approver = userEntityRepository.getReferenceById(responseId);
                return new ApprovalResponseEntity(entity, approver);
            })
            .toList();

        entity.addResponses(newResponses);
        approvalRequestEntityRepository.save(entity);

    }

    @Override
    public void save(Checklist checklist) {
        ProjectStepEntity projectStepEntity = projectStepEntityRepository.getReferenceById(
            checklist.getProjectStepId());
        UserEntity requester = userEntityRepository.getReferenceById(
            checklist.getUserId());
        List<ApprovalResponseEntity> approvalResponseEntities = approvalResponseEntityRepository.findAllByApprovalRequestId(
            checklist.getId());

        approvalRequestEntityRepository.save(
            mapper.toEntity(checklist, projectStepEntity, requester,
                approvalResponseEntities));
    }

}
