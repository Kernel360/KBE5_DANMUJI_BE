package com.back2basics.todolist.service;

import com.back2basics.todolist.port.in.DeleteTodoListUseCase;
import com.back2basics.todolist.port.out.TodoListCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTodoListService implements DeleteTodoListUseCase {

    private final TodoListCommandPort todoListCommandPort;

    @Override
    public void delete(Long checkListId) {
        todoListCommandPort.delete(checkListId);
    }
}
