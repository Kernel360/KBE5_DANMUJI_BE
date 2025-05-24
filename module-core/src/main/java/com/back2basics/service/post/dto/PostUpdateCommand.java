package com.back2basics.service.post.dto;

import com.back2basics.infra.post.custom.CustomTitleNotBlank;
import com.back2basics.model.post.PostStatus;
import com.back2basics.model.post.PostType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateCommand {

    private String requesterName;

    @CustomTitleNotBlank(message = "제목은 공백일 수 없습니다.")
    private String title;

    @Size(min = 1, message = "내용은 공백일 수 없습니다.")
    private String content;

    @CustomEnumValidator(enumClass = PostType.class, message = "올바른 enum type이 아닙니다")
    private PostType type;

    @CustomEnumValidator(enumClass = PostType.class, message = "올바른 enum type이 아닙니다")
    private PostStatus status;

    private Integer priority;
}