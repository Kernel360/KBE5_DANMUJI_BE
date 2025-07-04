package com.back2basics.adapter.persistence.todolist.entity;

import com.back2basics.adapter.persistence.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "todolists")
public class TodoListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt;

    @Column(name = "is_checked", nullable = false)
    private Boolean isChecked;

    public TodoListEntity(Long id, String content, UserEntity user,
        LocalDateTime createdAt, LocalDateTime checkedAt, Boolean isChecked) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.checkedAt = checkedAt;
        this.isChecked = isChecked;
    }
}
