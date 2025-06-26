package com.back2basics.todolist.port.in;

import com.back2basics.todolist.port.in.command.CreateTodoListCommand;

public interface UpdateTodoListUseCase {

    void update(Long checkListId, CreateTodoListCommand command);

    void check(Long checkListId);
}
