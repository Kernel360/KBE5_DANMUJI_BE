package com.back2basics.checklist.port.in;

import com.back2basics.checklist.port.in.command.CreateCheckListCommand;

public interface UpdateCheckListUseCase {

    void update(Long checkListId, CreateCheckListCommand command);

    void check(Long checkListId);
}
