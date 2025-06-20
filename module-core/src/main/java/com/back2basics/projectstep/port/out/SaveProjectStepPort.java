package com.back2basics.projectstep.port.out;

import com.back2basics.projectstep.model.ProjectStep;
import java.util.List;

public interface SaveProjectStepPort {

    void defaultSave(ProjectStep projectStep);

    void save(ProjectStep projectStep);

    void saveAll(List<ProjectStep> projectStepList);
}
