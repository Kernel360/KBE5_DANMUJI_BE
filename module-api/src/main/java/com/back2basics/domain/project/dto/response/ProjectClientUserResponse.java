package com.back2basics.domain.project.dto.response;

import com.back2basics.project.service.result.ProjectClientUserResult;
import java.util.List;

public record ProjectClientUserResponse(Long id, String name, String username, Long companyId,
                                        String companyName) {

    public static ProjectClientUserResponse from(ProjectClientUserResult result) {
        return new ProjectClientUserResponse(
            result.id(),
            result.name(),
            result.username(),
            result.companyId(),
            result.companyName()
        );
    }

    public static List<ProjectClientUserResponse> from(List<ProjectClientUserResult> results) {
        return results.stream()
            .map(ProjectClientUserResponse::from)
            .toList();
    }
}
