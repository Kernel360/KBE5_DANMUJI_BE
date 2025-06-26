package com.back2basics.adapter.persistence.todolist.mapper;

import com.back2basics.adapter.persistence.todolist.entity.TodoListEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.todolist.model.TodoList;
import org.springframework.stereotype.Component;

@Component
public class TodoListMapper {

    public TodoList toDomain(TodoListEntity entity) {
        return new TodoList(entity.getId(), entity.getContent(), entity.getUser().getId(),
            entity.getCreatedAt(), entity.getCheckedAt(), entity.getIsChecked());
    }

    public TodoListEntity toEntity(TodoList domain, UserEntity user) {
        return new TodoListEntity(domain.getId(), domain.getContent(), user,
            domain.getCreatedAt(), domain.getCheckedAt(), domain.getIsChecked());
    }
}
