package com.back2basics.todolist.port.out;

import com.back2basics.todolist.model.TodoList;
import java.util.List;

public interface TodoListQueryPort {

    TodoList findById(Long id);

    List<TodoList> findByUserId(Long userId);

    List<TodoList> findByPostId(Long postId, Long userId);

    List<TodoList> findByToday(Long userId);
}
