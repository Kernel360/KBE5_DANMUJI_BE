package com.back2basics.adapter.persistence.project;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectEntityRepository extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findByIdAndIsDeletedFalse(Long id);

    Page<ProjectEntity> findAllByIsDeletedFalse(Pageable pageable);

    @Query("""
            SELECT pu.project
            FROM ProjectUserEntity pu
            WHERE pu.user.id = :userId
              AND pu.project.isDeleted = false
        """)
    Page<ProjectEntity> findProjectsByUserId(@Param("userId") Long userId, Pageable pageable);

    Page<ProjectEntity> findAllByNameContainingAndIsDeletedFalse(Pageable pageable, String keyword);
}
