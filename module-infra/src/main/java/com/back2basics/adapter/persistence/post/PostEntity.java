package com.back2basics.adapter.persistence.post;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
// todo : ERD에 기반한 따른 필드 값 추가
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo : UserEntity생기면 @ManyToOne 필드로 수정
    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<>();

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
    public PostEntity(Long id, Long authorId, String title, String content, PostType type,
        Integer priority, PostStatus status, LocalDateTime completedAt,
        List<CommentEntity> comments) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.priority = priority;
        this.status = status;
        this.completedAt = completedAt;
        this.comments = comments;
    }

    public void addComment(CommentEntity comment) {
        comments.add(comment);
        comment.assignPost(this);
    }

    public void removeComment(CommentEntity comment) {
        comments.remove(comment);
        comment.assignPost(null);
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