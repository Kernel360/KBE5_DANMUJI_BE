package com.back2basics.adapter.persistence.board.post;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_post_id")
    private Long parentId;

    @Column(name = "author_ip")
    private String authorIp;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_step_id", nullable = false)
    private Long projectStepId;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 3000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PostType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private PostPriority priority;

    public PostEntity(Long id, Long parentId, Long projectId, String authorIp, Long authorId,
        String title,
        String content, PostType type, PostPriority priority,
        Long projectStepId) {
        this.id = id;
        this.parentId = parentId;
        this.authorIp = authorIp;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.priority = priority;
        this.projectId = projectId;
        this.projectStepId = projectStepId;
    }

    public static PostEntity of(Post post) {
        return new PostEntity(
            post.getId(),
            post.getParentId(),
            post.getProjectId(),
            post.getAuthorIp(),
            post.getAuthorId(),
            post.getTitle(),
            post.getContent(),
            post.getType(),
            post.getPriority(),
            post.getProjectStepId()
        );
    }

}