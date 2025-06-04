package com.back2basics.adapter.persistence.post;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// todo : ERD에 기반한 따른 필드 값 추가
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PostType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status = PostStatus.PENDING;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "completed_at")
    private LocalDateTime completedAt = null;

    @Builder
    public PostEntity(Long id, UserEntity author, String title, String content, PostType type,
        Integer priority, PostStatus status, LocalDateTime completedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.type = type;
        this.priority = priority;
        this.status = status;
        this.completedAt = completedAt;
    }

    public void update(String title, String content, PostType type, PostStatus status,
        int priority, LocalDateTime completedAt, boolean isDelete) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.status = status;
        this.priority = priority;
        this.completedAt = completedAt;
        if (isDelete) {
            this.markDeleted();
        }
    }
}