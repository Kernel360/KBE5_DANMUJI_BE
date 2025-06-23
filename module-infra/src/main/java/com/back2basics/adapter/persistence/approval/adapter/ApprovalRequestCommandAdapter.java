package com.back2basics.adapter.persistence.approval.adapter;

import com.back2basics.adapter.persistence.approval.entity.ApprovalRequestEntity;
import com.back2basics.adapter.persistence.approval.entity.ApprovalResponseEntity;
import com.back2basics.adapter.persistence.approval.mapper.ApprovalRequestMapper;
import com.back2basics.adapter.persistence.approval.repository.ApprovalRequestEntityRepository;
import com.back2basics.adapter.persistence.approval.repository.ApprovalResponseEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntity;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.approval.model.ApprovalRequest;
import com.back2basics.approval.model.ApprovalRequestStatus;
import com.back2basics.approval.port.out.ApprovalRequestCommandPort;
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
    public Long create(ApprovalRequest approvalRequest) {

        // 실제 쿼리를 즉시 실행하지 않고, 해당 ID에 대한 지연 프록시 객체 반환
        // 실제 값이 필요할 때까지 DB에 접근하지 않음
        UserEntity userEntity = userEntityRepository.getReferenceById(
            approvalRequest.getRequesterId());

        ProjectStepEntity projectStepEntity = projectStepEntityRepository.getReferenceById(
            approvalRequest.getProjectStepId());

        ApprovalRequestEntity entity = new ApprovalRequestEntity(projectStepEntity, userEntity,
            ApprovalRequestStatus.PENDING);

        List<ApprovalResponseEntity> responses = approvalRequest.getResponseIds().stream()
            .map(responseId -> {
                UserEntity response = userEntityRepository.getReferenceById(responseId);
                return new ApprovalResponseEntity(entity, response);
            })
            .toList();

        entity.addResponses(responses);
        approvalRequestEntityRepository.save(entity);
        return entity.getId();
    }

    @Override
    public void update(ApprovalRequest approvalRequest) {
        ApprovalRequestEntity entity = approvalRequestEntityRepository.getReferenceById(
            approvalRequest.getId());

        List<Long> existingIds = entity.getResponses().stream()
            .map(r -> r.getApprover().getId())
            .toList();

        List<ApprovalResponseEntity> newResponses = approvalRequest.getResponseIds().stream()
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
    public void save(ApprovalRequest approvalRequest) {
        ProjectStepEntity projectStepEntity = projectStepEntityRepository.getReferenceById(
            approvalRequest.getProjectStepId());
        UserEntity requester = userEntityRepository.getReferenceById(
            approvalRequest.getRequesterId());
        List<ApprovalResponseEntity> approvalResponseEntities = approvalResponseEntityRepository.findAllByApprovalRequestId(
            approvalRequest.getId());
        
        approvalRequestEntityRepository.save(
            mapper.toEntity(approvalRequest, projectStepEntity, requester,
                approvalResponseEntities));
    }

}
