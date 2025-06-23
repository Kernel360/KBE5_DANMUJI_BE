package com.back2basics.project.port.out;

import com.back2basics.project.model.Project;

public interface SaveProjectPort {

    Project save(Project project);

    Project createSave(Project project);
}
