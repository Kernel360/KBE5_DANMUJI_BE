package com.back2basics.domain.board.dto.request;

import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.board.post.port.in.command.PostSearchCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchRequest {

    @NotNull(message = "프로젝트를 입력하세요.")
    private Long projectId;

    @Nullable
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
    private PostPriority priority;

    @Nullable
    private PostType type;

    public PostSearchCommand toCommand() {
        return PostSearchCommand.builder()
            .title(title)
            .clientCompany(clientCompany)
            .developerCompany(developerCompany)
            .author(author)
            .priority(priority)
            .priority(priority)
            .type(type)
            .projectId(projectId)
            .projectStepId(stepId)
            .build();
    }
}