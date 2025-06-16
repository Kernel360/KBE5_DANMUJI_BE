package com.back2basics.project.port.out;

import com.back2basics.assignment.model.Assignment;
import java.util.List;

public interface SaveAssignmentPort {
    void save(Assignment assignment);

    void saveAll(List<Assignment> assignments);
}
