package com.back2basics.adapter.persistence.assignment.adapter;

import com.back2basics.adapter.persistence.assignment.AssignmentEntity;
import com.back2basics.adapter.persistence.assignment.AssignmentEntityRepository;
import com.back2basics.adapter.persistence.assignment.AssignmentMapper;
import com.back2basics.assignment.model.Assignment;
import com.back2basics.assignment.port.out.DeleteAssignmentPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAssignmentAdapter implements DeleteAssignmentPort {

    private final AssignmentEntityRepository assignmentEntityRepository;
    private final AssignmentMapper assignmentMapper;

    @Override
    public void deleteAll(List<Assignment> assignments) {
        List<AssignmentEntity> entities = assignments.stream()
            .map(assignmentMapper::toEntity)
            .toList();
        assignmentEntityRepository.deleteAllInBatch(entities);
    }
}
