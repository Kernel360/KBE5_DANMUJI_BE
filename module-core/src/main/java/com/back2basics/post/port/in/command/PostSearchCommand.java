package com.back2basics.post.port.in.command;


import com.back2basics.post.model.PostPriority;
import com.back2basics.post.model.PostType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSearchCommand {

    private String title;
    private String clientCompany;
    private String developerCompany;
    private String author;
    private Integer priority;
    private PostPriority status;
    private PostType type;
    private Long projectId;
    private Long projectStepId;
}
