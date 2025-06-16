package com.back2basics.post.port.in.command;

import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateCommand {

    private String title;
    private String content;
    private PostType type;
    private PostPriority priority;
}