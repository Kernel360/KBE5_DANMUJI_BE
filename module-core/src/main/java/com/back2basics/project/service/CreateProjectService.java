package com.back2basics.project.service;

import com.back2basics.project.model.Project;
import com.back2basics.project.port.in.CreateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.project.port.out.SaveProjectPort;
import com.back2basics.projectstep.model.ProjectStep;
import com.back2basics.projectstep.model.StepStatus;
import com.back2basics.projectstep.port.out.SaveProjectStepPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectService implements CreateProjectUseCase {

    private final SaveProjectPort saveProjectPort;
    private final SaveProjectStepPort saveProjectStepPort;

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

        /* todo: 디폴트 단계 추가되면 nameList로 반복
        *   default는 받아올 command가 없어서 도메인모델로 직접 생성 */

        ProjectStep projectStep = ProjectStep.builder()
            .name("요구사항정의")
            .projectId(saved.getId())
            .userId(null)
            .stepStatus(StepStatus.PENDING)
            .approvalStatus(null)
            .build();
        saveProjectStepPort.defaultSave(projectStep);
    }
}
