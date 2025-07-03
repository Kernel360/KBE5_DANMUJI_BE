package com.back2basics.adapter.persistence.checklist.repository;

import com.back2basics.adapter.persistence.checklist.entity.ApprovalEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalEntityRepository extends
    JpaRepository<ApprovalEntity, Long> {

    boolean existsByIdAndUserId(Long requestId, Long userId);

    @Query("select r.user.id from ApprovalEntity r where r.checklist.id = :checklistId")
    List<Long> findIdsByChecklistId(Long checklistId);

    List<ApprovalEntity> findAllByChecklistId(Long checklistId);

}
