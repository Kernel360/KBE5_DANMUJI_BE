package com.back2basics.todolist.service.result;

import com.back2basics.todolist.model.TodoList;
import java.time.LocalDateTime;

public record TodoListResult(Long id, String content, LocalDateTime createdAt,
                             boolean isChecked, LocalDateTime checkedAt) {

    public static TodoListResult from(TodoList todoList) {
        return new TodoListResult(
            todoList.getId(),
            todoList.getContent(),
            todoList.getCreatedAt(),
            todoList.getIsChecked(),
            todoList.getCheckedAt()
        );
    }
}
