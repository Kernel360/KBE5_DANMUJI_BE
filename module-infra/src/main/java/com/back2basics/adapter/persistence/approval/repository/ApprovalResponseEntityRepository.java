package com.back2basics.adapter.persistence.approval.repository;

import com.back2basics.adapter.persistence.approval.entity.ApprovalResponseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalResponseEntityRepository extends
    JpaRepository<ApprovalResponseEntity, Long> {

    boolean existsByIdAndApproverId(Long requestId, Long approverId);

    @Query("select r.approver.id from ApprovalResponseEntity r where r.approvalRequest.id = :requestId")
    List<Long> findApproverIdByApprovalRequestId(Long requestId);

    List<ApprovalResponseEntity> findAllByApprovalRequestId(Long requestId);
}
