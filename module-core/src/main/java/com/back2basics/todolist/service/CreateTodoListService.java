package com.back2basics.todolist.service;

import com.back2basics.todolist.model.TodoList;
import com.back2basics.todolist.port.in.CreateTodoListUseCase;
import com.back2basics.todolist.port.in.command.CreateTodoListCommand;
import com.back2basics.todolist.port.out.TodoListCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTodoListService implements CreateTodoListUseCase {

    private final TodoListCommandPort todoListCommandPort;

    @Override
    public void create(Long userId, Long postId, CreateTodoListCommand command) {
        TodoList todoList = TodoList.create(command.content(), userId, postId);
        todoListCommandPort.save(todoList);
    }
}
