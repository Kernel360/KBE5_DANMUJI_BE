package com.back2basics.board.post.model;

import com.back2basics.board.file.model.File;
import com.back2basics.board.link.model.Link;
import com.back2basics.board.post.port.in.command.PostCreateCommand;
import com.back2basics.board.post.port.in.command.PostUpdateCommand;
import com.back2basics.history.strategy.TargetDomain;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class Post implements TargetDomain {

    private final Long id;
    private final Long parentId;
    private final Long projectId;
    private Long projectStepId;
    private String authorIp;
    private Long authorId;
    private String authorName;
    private Role authorRole;
    private String title;
    private String content;
    private PostType type;
    private PostPriority priority;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private boolean isDelete;
    private List<File> files;
    private List<Link> links;

    public static Post create(PostCreateCommand command, Long userId, String userIp) {
        return new Post(
            null,
            command.getParentId(),
            command.getProjectId(),
            command.getStepId(),
            userIp,
            userId,
            null,
            null,
            command.getTitle(),
            command.getContent(),
            command.getType(),
            command.getPriority(),
            null, null, null,
            null, // ← files
            null // ← links
        );
    }

    public static Post create(
        Long id, Long parentId, Long projectId, Long projectStepId,
        String authorIp, Long authorId, String authorName, Role authorRole, String title,
        String content,
        PostType type, PostPriority priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt
    ) {
        return new Post(id, parentId, projectId, projectStepId, authorIp, authorId, authorName,
            authorRole,
            title, content, type, priority, createdAt, updatedAt, deletedAt, null, null);
    }

    // files 포함한 팩토리 메서드
    public static Post create(
        Long id, Long parentId, Long projectId, Long projectStepId,
        String authorIp, Long authorId, String authorName, Role authorRole, String title,
        String content,
        PostType type, PostPriority priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt, List<File> files, List<Link> links
    ) {
        return new Post(id, parentId, projectId, projectStepId, authorIp, authorId, authorName,
            authorRole,
            title, content, type, priority, createdAt, updatedAt, deletedAt, files, links);
    }


    public void update(PostUpdateCommand command, String userIp) {
        this.title = command.getTitle();
        this.content = command.getContent();
        this.type = command.getType();
        this.priority = command.getPriority();
        this.authorIp = userIp;
        this.projectStepId = command.getStepId();
    }

    public void markDeleted() {
        this.isDelete = true;
        this.deletedAt = LocalDateTime.now();
    }

    private Post(Long id, Long parentId, Long projectId, Long projectStepId, String authorIp,
        Long authorId, String authorName, Role authorRole, String title, String content,
        PostType type, PostPriority priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt, List<File> files, List<Link> links) {
        this.id = id;
        this.parentId = parentId;
        this.projectId = projectId;
        this.projectStepId = projectStepId;
        this.authorIp = authorIp;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorRole = authorRole;
        this.title = title;
        this.content = content;
        this.type = type;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDelete = false;
        this.files = files;
        this.links = links;
    }

    public static Post copyOf(Post post) {
        return Post.create(
            post.getId(),
            post.getParentId(),
            post.getProjectId(),
            post.getProjectStepId(),
            post.getAuthorIp(),
            post.getAuthorId(),
            post.getAuthorName(),
            post.getAuthorRole(),
            post.getTitle(),
            post.getContent(),
            post.getType(),
            post.getPriority(),
            post.getCreatedAt(),
            post.getUpdatedAt(),
            post.getDeletedAt(),
            post.getFiles(),
            post.getLinks()
        );
    }


    @Override
    public Long getId() {
        return this.id;
    }

}