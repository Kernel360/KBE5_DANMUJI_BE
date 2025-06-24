package com.back2basics.checklist.service;

import com.back2basics.checklist.model.CheckList;
import com.back2basics.checklist.port.in.CreateCheckListUseCase;
import com.back2basics.checklist.port.in.command.CreateCheckListCommand;
import com.back2basics.checklist.port.out.CheckListCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCheckListService implements CreateCheckListUseCase {

    private final CheckListCommandPort checkListCommandPort;

    @Override
    public void create(Long userId, Long postId, CreateCheckListCommand command) {
        CheckList checkList = CheckList.create(command.content(), userId, postId);
        checkListCommandPort.save(checkList);
    }
}
