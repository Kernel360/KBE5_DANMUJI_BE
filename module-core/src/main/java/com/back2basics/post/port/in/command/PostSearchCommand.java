package com.back2basics.post.port.in.command;


import com.back2basics.post.model.PostStatus;
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
    private PostStatus status;
    private PostType type;
    private Long projectStepId;
}
