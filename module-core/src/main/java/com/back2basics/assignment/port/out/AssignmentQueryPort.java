package com.back2basics.assignment.port.out;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.company.model.CompanyType;
import com.back2basics.user.model.UserType;
import java.util.List;

public interface AssignmentQueryPort {

    List<Assignment> findUsersByProjectId(Long projectId);

    Assignment findByProjectIdAndUserTypeAndCompanyType(Long projectId, UserType userType, CompanyType companyType);

    Assignment findByProjectIdAndUserId(Long projectId, Long userId);
}
