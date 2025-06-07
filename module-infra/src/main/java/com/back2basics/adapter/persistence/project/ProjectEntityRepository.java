package com.back2basics.adapter.persistence.project;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectEntityRepository extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findByIdAndIsDeletedFalse(Long id);

    Page<ProjectEntity> findAllByIsDeletedFalse(Pageable pageable);

    Page<ProjectEntity> findAllByNameContainingAndIsDeletedFalse(Pageable pageable, String keyword);
}
