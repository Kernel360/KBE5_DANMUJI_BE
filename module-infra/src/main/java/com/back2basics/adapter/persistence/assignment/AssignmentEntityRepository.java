package com.back2basics.adapter.persistence.assignment;

import com.back2basics.company.model.CompanyType;
import com.back2basics.user.model.UserType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentEntityRepository extends JpaRepository<AssignmentEntity, Long> {

    List<AssignmentEntity> findByProject_Id(Long projectId);

    AssignmentEntity findByProjectIdAndUserTypeAndCompanyType(Long projectId, UserType userType,
        CompanyType companyType);

    List<AssignmentEntity> findByProject_IdAndUser_Id(Long projectId, Long userId);

    List<AssignmentEntity> findByUserIdAndProjectIsDeletedFalse(Long userId);

    List<AssignmentEntity> findByProject_IdAndCompany_Id(Long projectId, Long companyId);

    @Query("SELECT a.user.id FROM AssignmentEntity a WHERE a.project.id = :projectId")
    List<Long> findUserIdsByProjectId(Long projectId);
}
