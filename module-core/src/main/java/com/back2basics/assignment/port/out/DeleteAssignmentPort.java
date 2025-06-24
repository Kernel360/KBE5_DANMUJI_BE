package com.back2basics.assignment.port.out;

import com.back2basics.assignment.model.Assignment;
import java.util.List;

public interface DeleteAssignmentPort {
    void deleteAll(List<Assignment> assignments);
}
