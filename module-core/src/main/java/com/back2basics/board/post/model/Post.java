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
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Post implements TargetDomain {

    private Long id;
    private Long parentId;
    private Long projectId;
    private Long projectStepId;
    private String authorIp;
    private Long authorId;
    private String authorName;
    private String authorUsername;
    private Role authorRole;
    private String title;
    private String content;
    private PostType type;
    private PostPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private boolean isDelete;
    private List<File> files;
    private List<Link> links;
    private Long commentCount;

    private String projectName;
    private String projectStepName;

    // postservice.create() 용 메소드
    public static Post createFromCommand(PostCreateCommand command, Long userId, String userIp) {
        return new Post(
            null,
            command.getParentId(),
            command.getProjectId(),
            command.getStepId(),
            userIp,
            userId,
            null,
            null,
            null,
            command.getTitle(),
            command.getContent(),
            command.getType(),
            command.getPriority(),
            null, null, null,
            null,
            null
        );
    }

    public static Post createWithoutFilesAndLinks(
        Long id, Long parentId, Long projectId, Long projectStepId,
        String authorIp, Long authorId, String authorName, String authorUsername, Role authorRole,
        String title,
        String content,
        PostType type, PostPriority priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt
    ) {
        return new Post(id, parentId, projectId, projectStepId, authorIp, authorId, authorName,
            authorUsername, authorRole, title, content, type, priority,
            createdAt, updatedAt, deletedAt, null, null);
    }

    // files 포함한 팩토리 메서드
    public static Post createWithFilesAndLinks(
        Long id, Long parentId, Long projectId, Long projectStepId,
        String authorIp, Long authorId, String authorName, String authorUsername, Role authorRole,
        String title,
        String content,
        PostType type, PostPriority priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt, List<File> files, List<Link> links
    ) {
        return new Post(id, parentId, projectId, projectStepId, authorIp, authorId, authorName,
            authorUsername, authorRole, title, content, type, priority,
            createdAt, updatedAt, deletedAt, files, links);
    }


    // summary용 팩토리 메서드
    public static Post createSummaryPost(
        Long id, Long parentId, Long projectId, Long projectStepId,
        String authorIp, Long authorId, String authorName, String authorUsername, Role authorRole,
        String title, String content, PostType type, PostPriority priority,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt, Long commentCount
    ) {
        return new Post(
            id, parentId, projectId, projectStepId, authorIp, authorId, authorName, authorUsername, authorRole,
            title, content, type, priority, createdAt, updatedAt, deletedAt, commentCount
        );
    }

    // createSummaryPost 전용 생성자
    private Post(Long id, Long parentId, Long projectId, Long projectStepId,
                 String authorIp, Long authorId, String authorName, String authorUsername, Role authorRole,
                 String title, String content, PostType type, PostPriority priority,
                 LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, Long commentCount) {
        this.id = id;
        this.parentId = parentId;
        this.projectId = projectId;
        this.projectStepId = projectStepId;
        this.authorIp = authorIp;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorUsername = authorUsername;
        this.authorRole = authorRole;
        this.title = title;
        this.content = content;
        this.type = type;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.commentCount = commentCount;
        this.isDelete = false;
    }

    private Post(Long id, Long parentId, Long projectId, Long projectStepId, String authorIp,
                 Long authorId, String authorName, String authorUsername, Role authorRole, String title,
                 String content, PostType type, PostPriority priority,
                 LocalDateTime createdAt, LocalDateTime updatedAt,
                 LocalDateTime deletedAt, List<File> files, List<Link> links) {
        this.id = id;
        this.parentId = parentId;
        this.projectId = projectId;
        this.projectStepId = projectStepId;
        this.authorIp = authorIp;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorUsername = authorUsername;
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

    // createDashboardPost() 용 생성자
    private Post(Long id, String title, LocalDateTime createdAt,
                 String projectName, String projectStepName,
                 String authorName, String authorUsername, Role authorRole,
                 PostPriority priority, PostType type) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.projectName = projectName;
        this.projectStepName = projectStepName;
        this.authorName = authorName;
        this.authorUsername = authorUsername;
        this.authorRole = authorRole;
        this.priority = priority;
        this.type = type;
    }

    public static Post createDashboardPost(Long id, String title, LocalDateTime createdAt,
                                           String projectName, String projectStepName,
                                           String authorName, String authorUsername, Role authorRole,
                                           PostPriority priority, PostType type) {
        return new Post(
            id, title, createdAt,
            projectName, projectStepName, authorName,
            authorUsername, authorRole, priority, type
        );
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

    public void restore() {
        this.isDelete = false;
        this.deletedAt = null;
    }

    public static Post copyOf(Post post) {
        return Post.createWithFilesAndLinks(
            post.getId(),
            post.getParentId(),
            post.getProjectId(),
            post.getProjectStepId(),
            post.getAuthorIp(),
            post.getAuthorId(),
            post.getAuthorName(),
            post.getAuthorUsername(),
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