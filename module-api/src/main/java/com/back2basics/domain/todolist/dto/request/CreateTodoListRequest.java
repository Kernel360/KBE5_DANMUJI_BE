package com.back2basics.domain.todolist.dto.request;

import com.back2basics.todolist.port.in.command.CreateTodoListCommand;

public record CreateTodoListRequest(String content) {

    public CreateTodoListCommand toCommand() {
        return new CreateTodoListCommand(this.content);
    }
}
