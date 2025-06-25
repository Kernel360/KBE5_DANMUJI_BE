package com.back2basics.adapter.persistence.checklist.entity;

import com.back2basics.adapter.persistence.board.post.PostEntity;
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
@Table(name = "checklists")
public class CheckListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt;

    @Column(name = "is_checked", nullable = false)
    private Boolean isChecked;

    public CheckListEntity(Long id, String content, UserEntity user, PostEntity post,
        LocalDateTime createdAt, LocalDateTime checkedAt, Boolean isChecked) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.post = post;
        this.createdAt = createdAt;
        this.checkedAt = checkedAt;
        this.isChecked = isChecked;
    }
}
