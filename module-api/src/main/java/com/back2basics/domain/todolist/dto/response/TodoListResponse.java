package com.back2basics.domain.todolist.dto.response;

import com.back2basics.todolist.service.result.TodoListResult;
import java.time.LocalDateTime;

public record TodoListResponse(Long id, String content, LocalDateTime createdAt,
                               boolean isChecked, LocalDateTime checkedAt) {

    public static TodoListResponse from(TodoListResult result) {
        return new TodoListResponse(result.id(), result.content(),
            result.createdAt(), result.isChecked(), result.checkedAt());
    }
}
