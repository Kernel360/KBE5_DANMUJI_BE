package com.back2basics.service.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateCommand {

    @NotBlank(message = "작성자는 필수입니다.")
    private String authorName;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public PostCreateCommand(String authorName, String title, String content) {
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }
}