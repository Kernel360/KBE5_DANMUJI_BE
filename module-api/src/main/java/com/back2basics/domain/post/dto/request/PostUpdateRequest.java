package com.back2basics.domain.post.dto.request;

import com.back2basics.infra.validation.custom.CustomEnumCheck;
import com.back2basics.post.model.PostPriority;
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

    @NotNull(message = "타입이 입력되지 않았습니다.")
    @CustomEnumCheck(enumClass = PostType.class, message = "올바른 enum type이 아닙니다")
    private PostType type;

    @NotNull(message = "우선순위가 입력되지 않았습니다.")
    @CustomEnumCheck(enumClass = PostPriority.class, message = "올바른 enum type이 아닙니다")
    private PostPriority priority;

    public PostUpdateCommand toCommand() {
        return PostUpdateCommand.builder()
            .title(title)
            .content(content)
            .type(type)
            .priority(priority)
            .build();
    }
}
