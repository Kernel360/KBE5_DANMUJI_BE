package com.back2basics.adapter.persistence.project;

import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.model.StatusCountProjection;
import io.lettuce.core.dynamic.annotation.Param;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectEntityRepository extends JpaRepository<ProjectEntity, Long> {

    ProjectEntity findByIdAndIsDeletedFalse(Long id);

    Page<ProjectEntity> findAllByIsDeletedFalse(Pageable pageable);

    @Query("""
         SELECT p
         FROM ProjectEntity p JOIN AssignmentEntity a ON a.project = p
         WHERE a.user.id = :userId
         AND p.isDeleted = false
         AND p.name LIKE %:keyword%
        """)
    Page<ProjectEntity> findByNameContainingAndUserIdIsDeletedFalse(
        @Param("keyword") String keyword,
        @Param("userId") Long userId, Pageable pageable);

    Page<ProjectEntity> findAllByNameContainingAndIsDeletedFalse(Pageable pageable, String keyword);

    @Query("""
          SELECT p
          FROM ProjectEntity p
          JOIN p.assignments pu
          WHERE pu.user.id = :userId
          AND p.isDeleted = false
        """)
    Page<ProjectEntity> findAllByUserId(
        @Param("userId") Long userId,
        Pageable pageable
    );

    Page<ProjectEntity> findAllByIsDeletedTrue(Pageable pageable);

    List<ProjectEntity> findTop5ByDeletedAtIsNullOrderByCreatedAtDesc();

    List<ProjectEntity> findAllByIsDeletedFalse();

    @Query("SELECT p FROM ProjectEntity p JOIN p.assignments pu " +
        "WHERE pu.user.id = :userId AND p.projectStatus = :status AND p.deletedAt IS NULL ORDER BY p.id DESC")
    List<ProjectEntity> findProjectsByUserIdAndStatus(@Param("userId") Long userId,
        @Param("status") ProjectStatus status);

    @Query("""
            SELECT p.projectStatus AS projectStatus, COUNT(p) AS count
            FROM ProjectEntity p
            WHERE p.isDeleted = false
            GROUP BY p.projectStatus
        """)
    List<StatusCountProjection> countProjectsByStatus();

    List<ProjectEntity> findAllByProjectStatusAndDeletedAtIsNullOrderByIdDesc(ProjectStatus status);

    ProjectEntity findByIdAndIsDeletedTrue(Long projectId);

    @Query("SELECT p.id FROM ProjectEntity p WHERE p.deletedAt < :threshold")
    List<Long> findIdsByDeletedAtBefore(LocalDateTime threshold);

    void deleteByIdIn(List<Long> deletedProjectIds);
}
