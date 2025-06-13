package com.back2basics.domain.project.dto.response;

import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.service.result.TestResult;
import com.back2basics.projectstep.model.ProjectStepStatus;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestResponse {

    private Long id;
    private String name;
    private String description;
    private String clientCompany;
    private String developCompany;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus projectStatus;

    public static TestResponse toResponse(TestResult result) {
        return TestResponse.builder()
            .id(result.getId())
            .name(result.getName())
            .description(result.getDescription())
            .clientCompany(result.getClientCompany())
            .developCompany(result.getDevelopCompany())
            .startDate(result.getStartDate())
            .endDate(result.getEndDate())
            .projectStatus(result.getProjectStatus())
            .build();
    }
}
