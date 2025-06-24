package com.back2basics.checklist.port.in;

import com.back2basics.checklist.port.in.command.CreateCheckListCommand;

public interface CreateCheckListUseCase {

    void create(Long userId, Long postId, CreateCheckListCommand command);
}
