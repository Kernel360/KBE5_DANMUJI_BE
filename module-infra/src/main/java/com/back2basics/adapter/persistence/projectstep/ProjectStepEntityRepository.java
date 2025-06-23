package com.back2basics.adapter.persistence.projectstep;

import com.back2basics.projectstep.model.ProjectStepStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStepEntityRepository extends JpaRepository<ProjectStepEntity, Long> {

    List<ProjectStepEntity> findAllByProjectIdAndDeletedAtIsNull(Long projectId);

    @Query("SELECT MAX(s.stepOrder) FROM ProjectStepEntity s WHERE s.project.id = :projectId AND s.deletedAt IS NULL")
    Integer findMaxStepOrderByProjectId(Long projectId);

    int countByProject_IdAndProjectStepStatus(Long projectId, ProjectStepStatus status); // completed step

    int countByProject_Id(Long projectId); // all step
}
