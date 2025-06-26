package com.back2basics.todolist.port.in;

import com.back2basics.todolist.service.result.TodoListResult;
import java.util.List;

public interface GetTodoListUseCase {

    List<TodoListResult> findByUserId(Long userId);

    List<TodoListResult> findByToday(Long userId);
}
