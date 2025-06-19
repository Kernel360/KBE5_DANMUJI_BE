package com.back2basics.adapter.persistence.projectstep;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStepEntityRepository extends JpaRepository<ProjectStepEntity, Long> {

    List<ProjectStepEntity> findAllByProjectIdAndDeletedAtIsNull(Long projectId);
}
