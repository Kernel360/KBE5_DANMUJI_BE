package com.back2basics.adapter.persistence.assignment.adapter;

import com.back2basics.adapter.persistence.assignment.AssignmentEntity;
import com.back2basics.adapter.persistence.assignment.AssignmentEntityRepository;
import com.back2basics.adapter.persistence.assignment.AssignmentMapper;
import com.back2basics.assignment.model.Assignment;
import com.back2basics.project.port.out.SaveProjectUserPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveAssignmentAdapter implements SaveProjectUserPort {

    private final AssignmentEntityRepository projectUserEntityRepository;
    private final AssignmentMapper assignmentMapper;

    @Override
    public void save(Assignment assignment) {
        AssignmentEntity entity = assignmentMapper.toEntity(assignment);
        projectUserEntityRepository.save(entity);
    }

    @Override
    public void saveAll(List<Assignment> assignments) {
        List<AssignmentEntity> entities = assignments.stream()
            .map(assignmentMapper::toEntity)
            .toList();
        projectUserEntityRepository.saveAll(entities);
    }
}