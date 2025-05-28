package com.back2basics.post.port.in.command;

import com.back2basics.infra.validation.custom.CustomEnumCheck;
import com.back2basics.infra.validation.custom.CustomNotBlank;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.model.PostType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateCommand {

    private String requesterName;

    @CustomNotBlank(message = "제목은 공백일 수 없습니다.")
    private String title;

    @Size(min = 1, message = "내용은 공백일 수 없습니다.")
    private String content;

    @CustomEnumCheck(enumClass = PostType.class, message = "올바른 enum type이 아닙니다")
    private PostType type;

    @CustomEnumCheck(enumClass = PostType.class, message = "올바른 enum type이 아닙니다")
    private PostStatus status;

    private Integer priority;
}