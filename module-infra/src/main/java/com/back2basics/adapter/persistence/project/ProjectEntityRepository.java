package com.back2basics.adapter.persistence.project;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectEntityRepository extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findByIdAndIsDeletedFalse(Long id);

    List<ProjectEntity> findAllByIsDeletedFalse();
}
