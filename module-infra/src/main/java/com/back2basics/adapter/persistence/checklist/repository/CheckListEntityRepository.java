package com.back2basics.adapter.persistence.checklist.repository;

import com.back2basics.adapter.persistence.checklist.entity.CheckListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListEntityRepository extends JpaRepository<CheckListEntity, Long> {

    CheckListEntity findByUserId(Long userId);
}
