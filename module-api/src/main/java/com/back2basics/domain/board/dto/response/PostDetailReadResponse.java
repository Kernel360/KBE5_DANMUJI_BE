package com.back2basics.domain.board.dto.response;

import com.back2basics.board.link.model.Link;
import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.board.post.service.result.PostDetailReadResult;
import com.back2basics.file.model.File;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailReadResponse {

    private final Long postId;
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
    private final boolean isDelete;
    private final List<File> files;
    private final List<Link> links;

    public static PostDetailReadResponse toResponse(PostDetailReadResult postDetails) {
        return PostDetailReadResponse.builder()
            .parentId(postDetails.getParentId())
            .postId(postDetails.getId())
            .authorIp(postDetails.getAuthorIp())
            .authorId(postDetails.getAuthorId())
            .authorName(postDetails.getAuthorName())
            .authorUsername(postDetails.getAuthorUsername())
            .projectId(postDetails.getProjectId())
            .projectStepId(postDetails.getProjectStepId())
            .title(postDetails.getTitle())
            .content(postDetails.getContent())
            .type(postDetails.getType())
            .priority(postDetails.getPriority())
            .createdAt(postDetails.getCreatedAt())
            .updatedAt(postDetails.getUpdatedAt())
            .isDelete(postDetails.isDeleted())
            .files(postDetails.getFiles())
            .links(postDetails.getLinks())
            .build();
    }

}
