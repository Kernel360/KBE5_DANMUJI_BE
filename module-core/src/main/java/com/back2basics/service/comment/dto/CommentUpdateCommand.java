package com.back2basics.service.comment.dto;

import com.back2basics.infra.validation.custom.CustomNotBlank;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateCommand {

    @NotBlank(message = "작성자는 필수입니다.")
    private String requesterName;

    @NotBlank(message = "내용은 필수입니다.")
    @CustomNotBlank(message = "댓글은 공백일 수 없습니다.")
    private String content;

    public CommentUpdateCommand(String requesterName, String content) {
        this.requesterName = requesterName;
        this.content = content;
    }
}