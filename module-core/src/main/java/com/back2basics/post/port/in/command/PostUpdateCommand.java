package com.back2basics.post.port.in.command;

import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostUpdateCommand {

    private String title;
    private String content;
    private PostType type;
    private PostPriority priority;
    private List<Long> fileIdsToDelete;
}