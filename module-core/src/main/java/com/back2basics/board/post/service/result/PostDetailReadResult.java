package com.back2basics.board.post.service.result;

import com.back2basics.board.link.model.Link;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.file.model.File;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailReadResult {

    private final Long id;
    private final Long parentId;
    private final Long projectId;
    private final Long projectStepId;
    private final String authorIp;
    private final Long authorId;
    private final String authorName;
    private final String authorUsername;
    private final String title;
    private final String content;
    private final PostType type;
    private final PostPriority priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean isDeleted;
    private final List<File> files;
    private final List<Link> links;


    public static PostDetailReadResult toResult(Post post) {
        return PostDetailReadResult.builder()
            .id(post.getId())
            .parentId(post.getParentId())
            .authorIp(post.getAuthorIp())
            .authorId(post.getAuthorId())
            .authorName(post.getAuthorName())
            .authorUsername(post.getAuthorUsername())
            .projectId(post.getProjectId())
            .projectStepId(post.getProjectStepId())
            .title(post.getTitle())
            .content(post.getContent())
            .type(post.getType())
            .priority(post.getPriority())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .isDeleted(post.getDeletedAt() != null)
            .files(post.getFiles())
            .links(post.getLinks())
            .build();
    }
}
