package com.back2basics.domain.project.dto.response;

import com.back2basics.project.service.result.ProjectRecentGetResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ProjectRecentGetResponse(Long id, String name, LocalDateTime createdAt) {


    public static ProjectRecentGetResponse from(ProjectRecentGetResult result) {
        return new ProjectRecentGetResponse(
            result.getId(),
            result.getName(),
            result.getCreatedAt()
        );
    }

    public static List<ProjectRecentGetResponse> from(List<ProjectRecentGetResult> result) {
        return result.stream()
            .map(ProjectRecentGetResponse::from)
            .collect(Collectors.toList());
    }
}
