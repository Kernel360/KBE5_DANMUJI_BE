package com.back2basics.todolist.service;

import com.back2basics.todolist.model.TodoList;
import com.back2basics.todolist.port.in.UpdateTodoListUseCase;
import com.back2basics.todolist.port.in.command.CreateTodoListCommand;
import com.back2basics.todolist.port.out.TodoListCommandPort;
import com.back2basics.todolist.port.out.TodoListQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTodoListService implements UpdateTodoListUseCase {

    private final TodoListCommandPort todoListCommandPort;
    private final TodoListQueryPort todoListQueryPort;

    @Override
    public void update(Long checkListId, CreateTodoListCommand command) {
        TodoList todoList = todoListQueryPort.findById(checkListId);
        todoList.update(command.content());
        todoListCommandPort.save(todoList);
    }

    @Override
    public void check(Long checkListId) {
        TodoList todoList = todoListQueryPort.findById(checkListId);

        if (todoList.getIsChecked()) {
            todoList.unCheck();
        } else {
            todoList.check();
        }

        todoListCommandPort.save(todoList);
    }
}
