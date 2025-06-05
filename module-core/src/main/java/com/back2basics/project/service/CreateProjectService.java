package com.back2basics.project.service;

import com.back2basics.company.model.CompanyType;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.project.port.out.SaveProjectPort;
import com.back2basics.project.port.out.SaveProjectUserPort;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.user.model.UserType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectService implements CreateProjectUseCase {

    private final SaveProjectPort saveProjectPort;
    private final SaveProjectStepPort saveProjectStepPort;
    private final SaveProjectUserPort saveProjectUserPort;
    private static final List<String> DEFAULT_STEPS =
        List.of("요구사항 정의", "화면설계", "디자인", "퍼블리싱", "개발", "검수");

    // todo: service 에서 build() 하는 것은 객체지향적이지 못함 -> 나중에 도메인에 메서드 추가
    // todo: save project  -> create step, userByProject(자동생성인데 manager, member 구분 enum 추가하려면 entity 생성 해주는게 맞는듯. 나중에 고민)
    @Override
    public void createProject(ProjectCreateCommand command) {
        Project project = Project.builder()
            .name(command.getName())
            .description(command.getDescription())
            .startDate(command.getStartDate())
            .endDate(command.getEndDate())
            .isDeleted(false)
            .build();
        Project savedProject = saveProjectPort.save(project);
        createDefaultSteps(savedProject.getId());
        createProjectUsers(savedProject.getId(), command);
    }

    private void createDefaultSteps(Long projectId) {
        List<String> defaultSteps = DEFAULT_STEPS;
        for (int i = 0; i < defaultSteps.size(); i++) {
            ProjectStep step = ProjectStep.create(
                defaultSteps.get(i),
                projectId,
                null,
                i + 1,
                ProjectStepStatus.PENDING
            );
            saveProjectStepPort.save(step);
        }
    }

    private void createProjectUsers(Long projectId, ProjectCreateCommand command) {
        saveProjectUserPort.save(
            ProjectUser.create(projectId, command.getDeveloperId(), command.getDevelopCompanyId(),
                CompanyType.DEVELOPER, UserType.MANAGER));
        saveProjectUserPort.save(
            ProjectUser.create(projectId, command.getClientId(), command.getClientCompanyId(),
                CompanyType.CLIENT, UserType.MANAGER));
    }
}
