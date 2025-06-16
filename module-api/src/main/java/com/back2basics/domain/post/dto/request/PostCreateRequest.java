package com.back2basics.domain.post.dto.request;

import com.back2basics.infra.validation.custom.CustomEnumCheck;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.in.command.PostCreateCommand;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @Nullable
    private Long parentId;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @CustomEnumCheck(enumClass = PostType.class, message = "올바른 enum type이 아닙니다")
    private PostType type;

    @CustomEnumCheck(enumClass = PostStatus.class, message = "올바른 enum type이 아닙니다")
    private PostStatus status;

    @NotNull(message = "우선순위가 입력되지 않았습니다.")
    private Integer priority;

    @NotNull(message = "프로젝트를 입력하세요.")
    private Long projectId;

    @NotNull(message = "프로젝트 단계를 입력하세요.")
    private Long stepId;

    public PostCreateCommand toCommand() {
        return PostCreateCommand.builder()
            .parentId(parentId)
            .title(title)
            .content(content)
            .status(status)
            .projectId(projectId)
            .stepId(stepId)
            .type(type)
            .priority(priority)
            .build();
    }
}
