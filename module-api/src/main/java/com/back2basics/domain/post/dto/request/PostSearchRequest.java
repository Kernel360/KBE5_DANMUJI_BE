package com.back2basics.domain.post.dto.request;

import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.in.command.PostSearchCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchRequest {

    @NotNull(message = "프로젝트 단계를 입력하세요.")
    private Long stepId;

    @Nullable
    private String title;

    @Nullable
    private String author;

    @Nullable
    private String clientCompany;

    @Nullable
    private String developerCompany;

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
            .projectStepId(stepId)
            .build();
    }
}