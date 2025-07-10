package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.ReadChecklistUseCase;
import com.back2basics.checklist.port.out.ChecklistQueryPort;
import com.back2basics.checklist.service.result.ChecklistInfoResult;
import com.back2basics.user.port.out.UserQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadChecklistService implements ReadChecklistUseCase {

    private final UserQueryPort userQueryPort;
    private final ChecklistQueryPort checklistQueryPort;

    @Override
    public ChecklistInfoResult findByChecklistId(Long checklistId) {
        Checklist request = checklistQueryPort.findById(checklistId);
        return new ChecklistInfoResult(request.getId(), request.getProjectStepId(),
            request.getUserId(), userQueryPort.findById(request.getUserId()).getName(),
            request.getTitle(), request.getContent(),
            request.getChecklistStatus(), request.getCreatedAt());
    }

    @Override
    public List<ChecklistInfoResult> findAll() {
        return checklistQueryPort.findAll().stream()
            .map(request -> new ChecklistInfoResult(
                request.getId(),
                request.getProjectStepId(),
                request.getUserId(),
                userQueryPort.findById(request.getUserId()).getName(),
                request.getTitle(),
                request.getContent(),
                request.getChecklistStatus(),
                request.getCreatedAt()
            ))
            .toList();
    }

    @Override
    public List<ChecklistInfoResult> findAllByStepId(Long stepId) {
        List<Checklist> checklists = checklistQueryPort.findAllByStepId(stepId);

        return checklists.stream()
            .map(request -> new ChecklistInfoResult(
                request.getId(),
                request.getProjectStepId(),
                request.getUserId(),
                userQueryPort.findById(request.getUserId()).getName(),
                request.getTitle(),
                request.getContent(),
                request.getChecklistStatus(),
                request.getCreatedAt()
            ))
            .toList();
    }

}
