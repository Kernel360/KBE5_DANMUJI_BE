package com.back2basics.assignment.port.out;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.company.model.CompanyType;
import com.back2basics.user.model.UserType;
import java.util.List;

public interface AssignmentQueryPort {

    List<Assignment> findAllByProjectId(Long projectId);

    List<Assignment> findAllByProjectIdAndCompanyId(Long projectId, Long companyId);

    CompanyType findCompanyTypeByProjectIdAndUserId(Long projectId, Long userId);

    UserType findUserTypeByProjectIdAndUserId(Long projectId, Long userId);
}
