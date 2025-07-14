package com.back2basics.adapter.persistence.checklist.repository;

import com.back2basics.adapter.persistence.checklist.entity.ChecklistEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistEntityRepository extends JpaRepository<ChecklistEntity, Long> {

    List<ChecklistEntity> findAllByProjectStepId(Long stepId);

    void deleteAllByProjectStepIdIn(List<Long> projectStepIds);
}
