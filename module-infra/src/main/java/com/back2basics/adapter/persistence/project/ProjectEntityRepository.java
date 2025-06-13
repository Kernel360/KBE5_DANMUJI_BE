package com.back2basics.adapter.persistence.project;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
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
    Page<ProjectEntity> findProjectsByUserIdAndIsDeletedFalse(Long userId,
        Pageable pageable);

    // todo:project에 projectUser 연관을 걸어서 이걸 사용해보기, 네이밍 안통하면 쿼리로 바꿔보기
//    @Query("""
//        SELECT p
//        FROM ProjectEntity p
//        JOIN p.projectUsers pu
//        WHERE pu.user.id    = :userId
//         AND p.isDeleted   = false
//        """)
    Page<ProjectEntity> findAllByProjectUsersUser_IdAndIsDeletedFalse(
        Long userId,
        Pageable pageable
    );

    @Query("""
          SELECT p
            FROM ProjectEntity p
            JOIN p.projectUsers pu
           WHERE pu.user.id = :userId
             AND p.isDeleted = false
        """)
    Page<ProjectEntity> findAllByUserId(
        @Param("userId") Long userId,
        Pageable pageable
    );


    Page<ProjectEntity> findAllByNameContainingAndIsDeletedFalse(Pageable pageable, String keyword);
}
