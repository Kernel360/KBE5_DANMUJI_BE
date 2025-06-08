package com.back2basics.adapter.persistence.projectuser;

import com.back2basics.company.model.CompanyType;
import com.back2basics.user.model.UserType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUserEntityRepository extends JpaRepository<ProjectUserEntity, Long> {

    List<ProjectUserEntity> findByProject_Id(Long projectId);

    ProjectUserEntity findByProjectIdAndUserTypeAndCompanyType(Long projectId, UserType userType, CompanyType companyType);

    ProjectUserEntity findByProjectIdAndUserId(Long projectId, Long userId);
}
