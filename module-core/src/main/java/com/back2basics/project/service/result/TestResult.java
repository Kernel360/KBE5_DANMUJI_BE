package com.back2basics.project.service.result;

import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.projectstep.model.ProjectStepStatus;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestResult {
    private Long id;
    private String name;
    private String description;
    private String clientCompany;
    private String developCompany;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus projectStatus;

    public static TestResult toResult(Project project) {
        // 고객사 -> project의 List<assignments> 중 회사타입이 고객사인 것의 첫번째를 찾아 회사이름을 반환
        String clientCompany = project.getProjectUsers().stream()
            .filter(user -> user.getCompanyType() == CompanyType.CLIENT) // assignments 중 고객사를 필터
            .findFirst() // 첫번째 반환
            .map(user -> user.getCompany().getName()) // 회사 이름
            .orElse(null);

        // 개발사
        String developerCompany = project.getProjectUsers().stream()
            .filter(user -> user.getCompanyType() == CompanyType.DEVELOPER)
            .findFirst()
            .map(user -> user.getCompany().getName())
            .orElse(null);

        return TestResult.builder()
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
