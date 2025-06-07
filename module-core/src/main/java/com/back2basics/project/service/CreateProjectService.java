package com.back2basics.project.service;


import com.back2basics.company.model.Company;
import com.back2basics.infra.validation.validator.CompanyValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.project.port.out.SaveProjectPort;
import com.back2basics.project.port.out.SaveProjectUserPort;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.ProjectStepStatus;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import com.back2basics.projectuser.model.ProjectUser;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectService implements CreateProjectUseCase {

    private final SaveProjectPort saveProjectPort;
    private final SaveProjectStepPort saveProjectStepPort;
    private final SaveProjectUserPort saveProjectUserPort;
    private final UserQueryPort userQueryPort;
    private final CompanyValidator companyValidator;
    private static final List<String> DEFAULT_STEPS =
        List.of("요구사항 정의", "화면설계", "디자인", "퍼블리싱", "개발", "검수");

    // todo: service 에서 build() 하는 것은 객체지향적이지 못함 -> 나중에 도메인에 메서드 추가
    // todo: save project  -> create step, userByProject(자동생성인데 manager, member 구분 enum 추가하려면 entity 생성 해주는게 맞는듯. 나중에 고민)
    @Override
    @Transactional
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
//        createProjectUsers(savedProject, command);

        User developer = userQueryPort.findById(command.getDeveloperId());
        User client = userQueryPort.findById(command.getClientId());
        Company developerCompany = companyValidator.findCompany(command.getDevelopCompanyId());
        Company clientCompany = companyValidator.findCompany(command.getClientCompanyId());

        List<User> developers = userQueryPort.findAllByCompanyId(command.getDevelopCompanyId());
        List<User> clients = userQueryPort.findAllByCompanyId(command.getClientCompanyId());

        List<ProjectUser> projectUsers = ProjectUser.createProjectUser(savedProject, developer, client, developerCompany, clientCompany, developers, clients);
        saveProjectUserPort.saveAll(projectUsers);
    }

    // todo: projectStep 도메인으로 옮기면 좋을 듯!
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

//    private void createProjectUsers(Project project, ProjectCreateCommand command) {
//        User developer = userQueryPort.findById(command.getDeveloperId());
//        User client = userQueryPort.findById(command.getClientId());
//        Company developerCompany = companyValidator.findCompany(command.getDevelopCompanyId());
//        Company clientCompany = companyValidator.findCompany(command.getClientCompanyId());
//
//        List<User> developers = userQueryPort.findAllByCompanyId(command.getDevelopCompanyId());
//        List<User> clients = userQueryPort.findAllByCompanyId(command.getClientCompanyId());
//
//        saveProjectUserPort.save(
//            ProjectUser.create(project, developer, developerCompany, UserType.MANAGER,
//                CompanyType.DEVELOPER));
//        saveProjectUserPort.save(
//            ProjectUser.create(project, client, clientCompany, UserType.MANAGER,
//                CompanyType.CLIENT));
//    }
}
