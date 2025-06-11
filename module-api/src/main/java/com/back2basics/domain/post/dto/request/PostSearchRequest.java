package com.back2basics.domain.post.dto.request;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.in.command.PostSearchCommand;
import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public class PostSearchRequest {

    @Nullable
    private String title;

    @Nullable
    private String clientCompany;

    @Nullable
    private String developerCompany;

    @Nullable
    private String author;

    @Nullable
    private Integer priority;

    @Nullable
    private PostStatus status;

    @Nullable
    private PostType type;

    public PostSearchCommand toCommand() {
        return PostSearchCommand.builder()
            .title(title)
            .clientCompany(clientCompany)
            .developerCompany(developerCompany)
            .author(author)
            .priority(priority)
            .status(status)
            .type(type)
            .build();
    }
}
