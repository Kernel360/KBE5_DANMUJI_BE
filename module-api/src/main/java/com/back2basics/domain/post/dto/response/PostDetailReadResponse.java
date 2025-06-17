package com.back2basics.domain.post.dto.response;

import com.back2basics.post.file.File;
import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import com.back2basics.post.service.result.PostDetailReadResult;
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
    private final String title;
    private final String content;
    private final PostType type;
    private final PostPriority priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean isDelete;
    private final List<File> files;


    public static PostDetailReadResponse toResponse(PostDetailReadResult postDetails) {
        return PostDetailReadResponse.builder()
            .parentId(postDetails.getParentId())
            .postId(postDetails.getId())
            .authorIp(postDetails.getAuthorIp())
            .authorId(postDetails.getAuthorId())
            .authorName(postDetails.getAuthorName())
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
            .build();
    }

}
