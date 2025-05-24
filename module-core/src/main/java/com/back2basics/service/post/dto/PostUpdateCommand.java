package com.back2basics.service.post.dto;

import com.back2basics.infra.custom.CustomTitleNotBlank;
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

    private PostType type;
    private PostStatus status;
    private Integer priority;
}