package com.back2basics.post.port.in.command;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCreateCommand {

    private Long parentId;
    private String title;
    private Long projectId;
    private String content;
    private PostType type;
    private PostStatus status;
    private Integer priority;
}