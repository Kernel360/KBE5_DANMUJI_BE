package com.back2basics.service.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateCommand {

    private String requesterName;

    @Size(min = 1, message = "제목은 공백일 수 없습니다.")
    private String title;

    @Size(min = 1, message = "내용은 공백일 수 없습니다.")
    private String content;

    public PostUpdateCommand(String title, String content, String requesterName) {
        this.title = title;
        this.content = content;
        this.requesterName = requesterName;
    }
}