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

    List<AssignmentEntity> findByProject_IdAndCompany_Id(Long projectId, Long companyId);

    @Query("SELECT a.user.id FROM AssignmentEntity a WHERE a.project.id = :projectId")
    List<Long> findUserIdsByProjectId(Long projectId);

    @Query("""
    SELECT a.companyType
    FROM AssignmentEntity a
    WHERE a.project.id = :projectId
      AND a.user.id = :userId
""")
    CompanyType findCompanyTypeByProject_IdAndUser_Id(Long projectId, Long userId);

    @Query("""
    SELECT a.userType
    FROM AssignmentEntity a
    WHERE a.project.id = :projectId
      AND a.user.id = :userId
""")
    UserType findUserTypeByProject_IdAndUser_Id(Long projectId, Long userId);
}
