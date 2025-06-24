package com.back2basics.checklist.service;

import com.back2basics.checklist.model.CheckList;
import com.back2basics.checklist.port.in.UpdateCheckListUseCase;
import com.back2basics.checklist.port.in.command.CreateCheckListCommand;
import com.back2basics.checklist.port.out.CheckListCommandPort;
import com.back2basics.checklist.port.out.CheckListQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCheckListService implements UpdateCheckListUseCase {

    private final CheckListCommandPort checkListCommandPort;
    private final CheckListQueryPort checkListQueryPort;

    @Override
    public void update(Long checkListId, CreateCheckListCommand command) {
        CheckList checkList = checkListQueryPort.findById(checkListId);
        checkList.update(command.content());
        checkListCommandPort.save(checkList);
    }

    @Override
    public void check(Long checkListId) {
        CheckList checkList = checkListQueryPort.findById(checkListId);

        if (checkList.getIsChecked()) {
            checkList.unCheck();
        } else {
            checkList.check();
        }

        checkListCommandPort.save(checkList);
    }
}
