package com.back2basics.todolist.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
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
    private final HistoryLogService historyLogService;

    @Override
    public void create(Long userId, CreateTodoListCommand command) {
        TodoList todoList = TodoList.create(command.content(), userId);
        TodoList savedTodoList = todoListCommandPort.save(todoList);

        historyLogService.logCreated(DomainType.TODOLIST, userId, savedTodoList, "체크리스트 신규 등록");
    }
}
