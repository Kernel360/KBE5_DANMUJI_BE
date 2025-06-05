package com.back2basics.projectuser.port.out;

import com.back2basics.projectuser.model.ProjectUser;
import java.util.List;

public interface ProjectUserQueryPort {

    List<ProjectUser> findUsersByProjectId(Long projectId);
}
