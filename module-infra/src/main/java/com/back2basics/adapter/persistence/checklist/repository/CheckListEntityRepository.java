package com.back2basics.adapter.persistence.checklist.repository;

import com.back2basics.adapter.persistence.checklist.entity.CheckListEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListEntityRepository extends JpaRepository<CheckListEntity, Long> {

    List<CheckListEntity> findAllByUserId(Long userId);

    List<CheckListEntity> findAllByPostIdAndUserId(Long postId, Long userId);

    @Query("SELECT c FROM CheckListEntity c WHERE c.user.id = :userId AND c.createdAt >= :start AND c.createdAt < :end")
    List<CheckListEntity> findByToday(@Param("userId") Long userId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end);
}
