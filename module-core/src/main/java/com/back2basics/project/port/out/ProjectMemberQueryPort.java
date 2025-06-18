package com.back2basics.project.port.out;

import java.util.List;

public interface ProjectMemberQueryPort {

    List<Long> getUserIdsByProject(Long projectId);
}
