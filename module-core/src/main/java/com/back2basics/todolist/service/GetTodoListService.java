package com.back2basics.todolist.service;

import com.back2basics.todolist.model.TodoList;
import com.back2basics.todolist.port.in.GetTodoListUseCase;
import com.back2basics.todolist.port.out.TodoListQueryPort;
import com.back2basics.todolist.service.result.TodoListResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTodoListService implements GetTodoListUseCase {

    private final TodoListQueryPort todoListQueryPort;

    @Override
    public List<TodoListResult> findByUserId(Long userId) {
        List<TodoList> todoLists = todoListQueryPort.findByUserId(userId);

        return todoLists.stream()
            .map(TodoListResult::from)
            .toList();
    }

    @Override
    public List<TodoListResult> findByToday(Long userId) {
        List<TodoList> todoLists = todoListQueryPort.findByToday(userId);
        return todoLists.stream()
            .map(TodoListResult::from)
            .toList();
    }
}
