package com.back2basics.checklist.service;

import com.back2basics.checklist.port.in.DeleteCheckListUseCase;
import com.back2basics.checklist.port.out.CheckListCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCheckListService implements DeleteCheckListUseCase {

    private final CheckListCommandPort checkListCommandPort;

    @Override
    public void delete(Long checkListId) {
        checkListCommandPort.delete(checkListId);
    }
}
