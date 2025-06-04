package com.back2basics.adapter.persistence.projectuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUserEntityRepository extends JpaRepository<ProjectUserEntity, Long> {

}
