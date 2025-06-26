package com.back2basics.todolist.port.out;

import com.back2basics.todolist.model.TodoList;

public interface TodoListCommandPort {

    TodoList save(TodoList todoList);

    void delete(Long checkListId);
}
