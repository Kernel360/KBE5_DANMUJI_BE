package com.back2basics.board.post.port.in.command;

import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
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
    private Long stepId;
    private List<Long> fileIdsToDelete;
    private List<Long> linkIdsToDelete;
    private List<String> newLinks;
}