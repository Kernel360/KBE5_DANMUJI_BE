package com.back2basics.domain.board.dto.request;

import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.board.post.port.in.command.PostCreateCommand;
import com.back2basics.custom.CustomEnumCheck;
import com.back2basics.custom.CustomLinkCheck;
import com.back2basics.custom.CustomStringLengthCheck;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @Nullable
    private Long parentId;

    @NotBlank(message = "제목은 필수입니다.")
    @CustomStringLengthCheck(min = 1, max = 50, message = "게시글 제목은 30자 이하로 입력해주세요")
    private String title;

    @NotBlank(message = "내용이 비어있을 수 없습니다.")
    @CustomStringLengthCheck(min = 1, max = 50, message = "게시글 내용은 1,000자 이하로 입력해주세요")
    private String content;

    @NotNull(message = "타입이 입력되지 않았습니다.")
    @CustomEnumCheck(enumClass = PostType.class, message = "올바른 enum type이 아닙니다")
    private PostType type;

    @NotNull(message = "우선순위가 입력되지 않았습니다.")
    @CustomEnumCheck(enumClass = PostPriority.class, message = "올바른 enum type이 아닙니다")
    private PostPriority priority;

    @NotNull(message = "프로젝트를 입력하세요.")
    private Long projectId;

    @NotNull(message = "프로젝트 단계를 입력하세요.")
    private Long stepId;

    @CustomLinkCheck
    private List<String> newLinks = List.of();

    public PostCreateCommand toCommand() {
        return PostCreateCommand.builder()
            .parentId(parentId)
            .title(title)
            .content(content)
            .priority(priority)
            .projectId(projectId)
            .stepId(stepId)
            .type(type)
            .newLinks(newLinks)
            .build();
    }
}
