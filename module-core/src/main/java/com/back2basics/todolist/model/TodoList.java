package com.back2basics.todolist.model;

import com.back2basics.history.strategy.TargetDomain;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodoList implements TargetDomain {

    private final Long id;
    private String content;
    private final Long userId;
    private final LocalDateTime createdAt;
    private LocalDateTime checkedAt;
    private Boolean isChecked;

    public TodoList(Long id, String content, Long userId, LocalDateTime createdAt,
        LocalDateTime checkedAt,
        Boolean isChecked) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.createdAt = createdAt;
        this.checkedAt = checkedAt;
        this.isChecked = isChecked;
    }

    public static TodoList create(String content, Long userId) {
        return new TodoList(null, content, userId, LocalDateTime.now(), null, false);
    }

    public void update(String newContent) {
        this.content = newContent;
    }

    public void check() {
        this.isChecked = true;
        this.checkedAt = LocalDateTime.now();
    }

    public void unCheck() {
        this.isChecked = false;
        this.checkedAt = null;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public static TodoList copyOf(TodoList original) {
        return new TodoList(
            original.getId(),
            original.getContent(),
            original.getUserId(),
            original.getCreatedAt(),
            original.getCheckedAt(),
            original.getIsChecked()
        );
    }

}
