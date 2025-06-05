package com.back2basics.project.service;

import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.project.port.out.SaveProjectPort;
import com.back2basics.project.port.out.SaveProjectUserPort;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.StepStatus;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import com.back2basics.projectuser.model.ProjectUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectService implements CreateProjectUseCase {

    private final SaveProjectPort saveProjectPort;
    private final SaveProjectStepPort saveProjectStepPort;
    private final SaveProjectUserPort saveProjectUserPort;

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
        Project saved = saveProjectPort.save(project);

        /* todo: 디폴트 단계 추가되면 nameList로 반복 (승인자는 고객사 담당자로 할지 고민 일단 1L로)
         *   default는 받아올 command가 없어서 도메인모델로 직접 생성 */

        ProjectStep projectStep = ProjectStep.builder()
            .name("요구사항정의")
            .projectId(saved.getId())
            .userId(null)
            .stepStatus(StepStatus.IN_PROGRESS)
            .approvalStatus(null)
            .build();
        saveProjectStepPort.defaultSave(projectStep);

        // 커멘드안에
        List<ProjectUser> projectUsers = ProjectUser.createProjectUser(command, saved);
        for (ProjectUser projectUser : projectUsers) {
            saveProjectUserPort.save(projectUser);
        }
    }
}
