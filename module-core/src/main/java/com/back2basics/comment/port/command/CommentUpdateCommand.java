package com.back2basics.comment.port.command;

import com.back2basics.infra.validation.custom.CustomNotBlank;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentUpdateCommand {

    // todo : 식별값 id로 변경
    @NotBlank(message = "작성자는 필수입니다.")
    private String requesterName;

    @NotBlank(message = "내용은 필수입니다.")
    @CustomNotBlank(message = "댓글은 공백일 수 없습니다.")
    private String content;
}