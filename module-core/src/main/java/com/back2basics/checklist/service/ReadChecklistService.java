package com.back2basics.checklist.service;

import com.back2basics.checklist.model.Checklist;
import com.back2basics.checklist.port.in.ReadChecklistUseCase;
import com.back2basics.checklist.port.out.ChecklistQueryPort;
import com.back2basics.checklist.service.result.ChecklistInfoResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadChecklistService implements ReadChecklistUseCase {

    private final ChecklistQueryPort checklistQueryPort;

    @Override
    public ChecklistInfoResult findByRequestId(Long requestId) {
        Checklist request = checklistQueryPort.findById(requestId);
        return new ChecklistInfoResult(request.getId(), request.getProjectStepId(),
            request.getUserId(), request.getTitle(), request.getContent(),
            request.getChecklistStatus(), request.getRequestedAt());
    }

    @Override
    public List<ChecklistInfoResult> findAll() {
        return checklistQueryPort.findAll().stream()
            .map(request -> new ChecklistInfoResult(
                request.getId(),
                request.getProjectStepId(),
                request.getUserId(),
                request.getTitle(),
                request.getContent(),
                request.getChecklistStatus(),
                request.getCompletedAt()
            ))
            .toList();
    }

}
