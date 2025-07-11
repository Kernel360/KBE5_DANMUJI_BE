package com.back2basics.domain.board.dto.request;

import com.back2basics.board.post.model.PostPriority;
import com.back2basics.board.post.model.PostType;
import com.back2basics.board.post.port.in.command.PostCreateCommand;
import com.back2basics.custom.CustomEnumCheck;
import com.back2basics.infra.s3.dto.PresignedUploadCompleteInfo;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

@Getter
public class PostCreateWithPresignedRequest {

    @Nullable
    private Long parentId;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
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

    private List<String> newLinks = List.of();
    private List<PresignedUploadedFileRequest> uploadedFiles;

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

    public List<PresignedUploadCompleteInfo> toUploadedFileInfos() {
        return this.uploadedFiles.stream()
            .map(file -> PresignedUploadCompleteInfo.of(
                file.getOriginalName(),
                file.getUrl(),
                file.getExtension(),
                file.getSize()
            ))
            .toList();
    }
}