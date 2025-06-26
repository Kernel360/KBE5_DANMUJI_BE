package com.back2basics.todolist.port.in;

import com.back2basics.todolist.port.in.command.CreateTodoListCommand;

public interface CreateTodoListUseCase {

    void create(Long userId, CreateTodoListCommand command);
}
