package com.back2basics.board.post.port.in.command;


import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSearchCommand {

    private String title;
    private String clientCompany;
    private String developerCompany;
    private String author;
    private PostPriority priority;
    private PostType type;
    private Long projectId;
    private Long projectStepId;
}
