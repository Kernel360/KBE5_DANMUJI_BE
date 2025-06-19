package com.back2basics.domain.assignment.dto.response;

import com.back2basics.assignment.service.result.AssignProjectListResult;
import java.util.List;

public record AssignProjectListResponse(Long id, String companyName,
                                        List<AssignUserResponse> assignUsers) {

    public static AssignProjectListResponse toResponse(AssignProjectListResult result) {
        return new AssignProjectListResponse(result.id(), result.companyName(),
            result.assignUsers().stream().map(AssignUserResponse::toResponse).toList());
    }
}
