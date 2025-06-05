package com.back2basics.project.port.out;

import com.back2basics.projectuser.model.ProjectUser;
import java.util.List;

public interface SaveProjectUserPort {
    void save(ProjectUser projectUser);
}
