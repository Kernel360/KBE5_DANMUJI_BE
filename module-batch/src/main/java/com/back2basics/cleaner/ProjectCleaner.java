package com.back2basics.cleaner;

import com.back2basics.SoftDeletableCleaner;
import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
import com.back2basics.adapter.persistence.checklist.repository.ChecklistEntityRepository;
import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
import com.back2basics.adapter.persistence.projectstep.ProjectStepEntityRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectCleaner implements SoftDeletableCleaner {

    private final ProjectEntityRepository repository;
    private final PostEntityRepository postEntityRepository;
    private final ProjectStepEntityRepository projectStepEntityRepository;
    private final ChecklistEntityRepository checklistEntityRepository;

    @Override
    public void clean(LocalDateTime threshold) {
        List<Long> deletedProjectIds = repository.findIdsByDeletedAtBefore(threshold);
        List<Long> projectStepIds = projectStepEntityRepository.findIdsByProjectIdIn(
            deletedProjectIds);

        if (!deletedProjectIds.isEmpty()) {
            postEntityRepository.deleteAllByProjectIdIn(deletedProjectIds);
            checklistEntityRepository.deleteAllByProjectStepIdIn(projectStepIds);
            projectStepEntityRepository.deleteAllByProjectIdIn(deletedProjectIds);

            repository.deleteByIdIn(deletedProjectIds);
        }
    }

    @Override
    public String getName() {
        return "Project";
    }
}
