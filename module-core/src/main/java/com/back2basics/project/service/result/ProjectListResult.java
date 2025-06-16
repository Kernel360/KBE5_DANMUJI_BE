package com.back2basics.project.service.result;

import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectListResult {
    private Long id;
    private String name;
    private String description;
    private String clientCompany;
    private String developCompany;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus projectStatus;

    public static ProjectListResult toResult(Project project) {
        // 고객사 -> project의 List<assignments> 중 회사타입이 고객사인 것의 첫번째를 찾아 회사이름을 반환
        String clientCompany = project.getAssignments().stream()
            .filter(user -> user.getCompanyType() == CompanyType.CLIENT) // assignments 중 고객사를 필터
            .findFirst() // 첫번째 반환
            .map(user -> user.getCompany().getName()) // 회사 이름
            .orElse(null);

        // 개발사
        String developerCompany = project.getAssignments().stream()
            .filter(user -> user.getCompanyType() == CompanyType.DEVELOPER)
            .findFirst()
            .map(user -> user.getCompany().getName())
            .orElse(null);

        return ProjectListResult.builder()
            .id(project.getId())
            .name(project.getName())
            .clientCompany(clientCompany)
            .developCompany(developerCompany)
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .projectStatus(project.getStatus())
            .build();
    }
}
