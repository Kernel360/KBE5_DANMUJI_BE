package com.back2basics.adapter.persistence.approval.repository;

import com.back2basics.adapter.persistence.approval.entity.ApprovalResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalResponseEntityRepository extends JpaRepository<ApprovalResponseEntity, Long> {

    boolean existsByIdAndApproverId(Long requestId, Long approverId);
}
