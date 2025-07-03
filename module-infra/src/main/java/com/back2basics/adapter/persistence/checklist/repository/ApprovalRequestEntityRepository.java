package com.back2basics.adapter.persistence.checklist.repository;

import com.back2basics.adapter.persistence.checklist.entity.ChecklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRequestEntityRepository extends JpaRepository<ChecklistEntity, Long> {

}
