package com.back2basics.service.comment.dto;

import com.back2basics.infra.post.custom.CustomTitleNotBlank;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateCommand {

    @NotBlank(message = "작성자는 필수입니다.")
    private String requesterName;

    @NotBlank(message = "내용은 필수입니다.")
    @CustomTitleNotBlank(message = "댓글은 공백일 수 없습니다.") // todo : 커스텀어노테이션 이름 변경
    private String content;

    public CommentUpdateCommand(String requesterName, String content) {
        this.requesterName = requesterName;
        this.content = content;
    }
}