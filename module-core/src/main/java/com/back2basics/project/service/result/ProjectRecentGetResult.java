package com.back2basics.project.service.result;

import com.back2basics.project.model.Project;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectRecentGetResult {

    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;

    @Builder
    public ProjectRecentGetResult(Long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static ProjectRecentGetResult toResult(Project project) {
        return ProjectRecentGetResult.builder()
            .id(project.getId())
            .name(project.getName())
            .createdAt(project.getCreatedAt())
            .build();
    }
}
