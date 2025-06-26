package com.back2basics.adapter.persistence.todolist.adapter;

import static com.back2basics.infra.exception.todolist.TodoListErrorCode.CHECK_LIST_NOT_FOUND;

import com.back2basics.adapter.persistence.todolist.entity.TodoListEntity;
import com.back2basics.adapter.persistence.todolist.mapper.TodoListMapper;
import com.back2basics.adapter.persistence.todolist.repository.TodoListEntityRepository;
import com.back2basics.todolist.model.TodoList;
import com.back2basics.todolist.port.out.TodoListQueryPort;
import com.back2basics.infra.exception.todolist.TodoListException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoListQueryAdapter implements TodoListQueryPort {

    private final TodoListMapper mapper;
    private final TodoListEntityRepository todoListEntityRepository;

    @Override
    public TodoList findById(Long id) {
        TodoListEntity todoListEntity = todoListEntityRepository.findById(id).orElseThrow(
            () -> new TodoListException(CHECK_LIST_NOT_FOUND));
        return mapper.toDomain(todoListEntity);
    }

    @Override
    public List<TodoList> findByUserId(Long userId) {
        return todoListEntityRepository.findAllByUserId(userId).stream()
            .map(mapper::toDomain).toList();
    }

    @Override
    public List<TodoList> findByPostId(Long postId, Long userId) {
        return todoListEntityRepository.findAllByPostIdAndUserId(postId, userId).stream()
            .map(mapper::toDomain).toList();
    }

    @Override
    public List<TodoList> findByToday(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        return todoListEntityRepository.findByToday(userId, start, end).stream()
            .map(mapper::toDomain).toList();
    }
}
