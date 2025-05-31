package com.back2basics.adapter.persistence.projectstep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStepEntityRepository extends JpaRepository<ProjectStepEntity, Long> {
}
