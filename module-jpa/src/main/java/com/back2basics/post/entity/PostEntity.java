package com.back2basics.post.entity;

import com.back2basics.comment.entity.CommentEntity;
import com.back2basics.common.entity.BaseTimeEntity;
import com.back2basics.model.post.PostStatus;
import com.back2basics.model.post.PostType;
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
    @Column(name = "author_name", nullable = false)
    private String authorName;

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

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt = null;

    @Column(name = "completed_at")
    private LocalDateTime completedAt = null;

    @Builder
    public PostEntity(Long id, String authorName, String title, String content, PostType type,
        Integer priority, LocalDateTime deletedAt,
        PostStatus status, LocalDateTime completedAt,
        List<CommentEntity> comments) {
        this.id = id;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.type = type;
        this.priority = priority;
        this.deletedAt = deletedAt;
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
}