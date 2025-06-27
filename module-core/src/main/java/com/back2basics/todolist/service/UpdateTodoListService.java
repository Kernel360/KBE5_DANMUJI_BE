package com.back2basics.todolist.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
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
    private final HistoryLogService historyLogService;

    @Override
    public void update(Long checkListId, CreateTodoListCommand command, Long loggedInUserId) {
        TodoList todoList = todoListQueryPort.findById(checkListId);
        TodoList before = TodoList.copyOf(todoList);
        todoList.update(command.content());

        historyLogService.logUpdated(DomainType.CHECK_LIST, loggedInUserId, before, todoList,
            "체크리스트 정보 변경");
    }

    @Override
    public void check(Long checkListId, Long loggedInUserId) {
        TodoList todoList = todoListQueryPort.findById(checkListId);
        TodoList before = TodoList.copyOf(todoList);

        if (todoList.getIsChecked()) {
            todoList.unCheck();
        } else {
            todoList.check();
        }

        todoListCommandPort.save(todoList);

        historyLogService.logUpdated(DomainType.CHECK_LIST, loggedInUserId, before, todoList,
            "체크리스트 정보 변경");
    }
}
