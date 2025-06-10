package com.back2basics.projectuser.port.out;

import com.back2basics.company.model.CompanyType;
import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.user.model.UserType;
import java.util.List;

public interface ProjectUserQueryPort {

    List<ProjectUser> findUsersByProjectId(Long projectId);

    ProjectUser findByProjectIdAndUserTypeAndCompanyType(Long projectId, UserType userType, CompanyType companyType);

    ProjectUser findByProjectIdAndUserId(Long projectId, Long userId);
}
