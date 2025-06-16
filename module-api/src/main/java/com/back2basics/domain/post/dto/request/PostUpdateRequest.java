package com.back2basics.domain.post.dto.request;

import com.back2basics.infra.validation.custom.CustomEnumCheck;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import com.back2basics.post.port.in.command.PostUpdateCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostUpdateRequest {

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

    public PostUpdateCommand toCommand() {
        return PostUpdateCommand.builder()
            .title(title)
            .content(content)
            .status(status)
            .type(type).priority(priority)
            .build();
    }
}
