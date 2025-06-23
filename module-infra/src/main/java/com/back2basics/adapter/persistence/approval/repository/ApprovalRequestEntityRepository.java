package com.back2basics.adapter.persistence.approval.repository;

import com.back2basics.adapter.persistence.approval.entity.ApprovalRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRequestEntityRepository extends JpaRepository<ApprovalRequestEntity, Long> {

}
