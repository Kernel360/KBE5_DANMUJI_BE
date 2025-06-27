package com.back2basics.todolist.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.todolist.model.TodoList;
import com.back2basics.todolist.port.in.DeleteTodoListUseCase;
import com.back2basics.todolist.port.out.TodoListCommandPort;
import com.back2basics.todolist.port.out.TodoListQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTodoListService implements DeleteTodoListUseCase {

    private final TodoListCommandPort todoListCommandPort;
    private final TodoListQueryPort todoListQueryPort;
    private final HistoryLogService historyLogService;

    @Override
    public void delete(Long checkListId, Long loggedInUserId) {
        todoListCommandPort.delete(checkListId);
        
        TodoList deletedTodoList = todoListQueryPort.findById(checkListId);
        historyLogService.logDeleted(DomainType.CHECK_LIST, loggedInUserId, deletedTodoList,
            "체크리스트 삭제");
    }
}
