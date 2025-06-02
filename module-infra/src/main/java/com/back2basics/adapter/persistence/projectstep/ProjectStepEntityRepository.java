package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.projectstep.model.ProjectStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectStepEntityRepository extends JpaRepository<ProjectStepEntity, Long> {

    List<ProjectStepEntity> findAllByProjectId(Long projectId);
}
