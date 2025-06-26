package com.back2basics.adapter.persistence.todolist.adapter;

import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
import com.back2basics.adapter.persistence.todolist.entity.TodoListEntity;
import com.back2basics.adapter.persistence.todolist.mapper.TodoListMapper;
import com.back2basics.adapter.persistence.todolist.repository.TodoListEntityRepository;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.todolist.model.TodoList;
import com.back2basics.todolist.port.out.TodoListCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class TodoTodoListCommandAdapter implements TodoListCommandPort {

    private final TodoListMapper mapper;
    private final TodoListEntityRepository todoListEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final PostEntityRepository postEntityRepository;

    @Override
    public void save(TodoList todoList) {
        UserEntity user = userEntityRepository.getReferenceById(todoList.getUserId());
        TodoListEntity todoListEntity = mapper.toEntity(todoList, user);
        todoListEntityRepository.save(todoListEntity);
    }

    @Override
    public void delete(Long checkListId) {
        todoListEntityRepository.deleteById(checkListId);
    }
}
