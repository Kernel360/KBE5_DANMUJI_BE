package com.back2basics.service.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateCommand {

    private String requesterName;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public PostUpdateCommand(String title, String content, String requesterName) {
        this.title = title;
        this.content = content;
        this.requesterName = requesterName;
    }
}