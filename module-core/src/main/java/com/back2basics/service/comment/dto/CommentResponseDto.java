package com.back2basics.service.comment.dto;

import com.back2basics.comment.model.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponseDto {

    private final Long id;
    private final Long postId;
    private final Long parentId;
    private final String authorName;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private final List<CommentResponseDto> children;

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
            .id(comment.getId())
            .postId(comment.getPostId())
            .parentId(comment.getParentCommentId())
            .authorName(comment.getAuthorName())
            .content(comment.getContent())
            .createdAt(comment.getCreatedAt())
            .updatedAt(comment.getUpdatedAt())
            .children(comment.getChildren().stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toCollection((ArrayList::new))))
            .build();
    }
}
