package com.back2basics.project.service;

import com.back2basics.assignment.model.Assignment;
import com.back2basics.assignment.port.out.DeleteAssignmentPort;
import com.back2basics.company.model.CompanyType;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.project.model.Project;
import com.back2basics.project.model.ProjectStatus;
import com.back2basics.project.port.in.UpdateProjectUseCase;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import com.back2basics.project.port.out.ReadProjectPort;
import com.back2basics.project.port.out.SaveProjectUserPort;
import com.back2basics.project.port.out.UpdateProjectPort;
import com.back2basics.assignment.port.out.AssignmentQueryPort;
import com.back2basics.user.model.User;
import com.back2basics.user.model.UserType;
import com.back2basics.user.port.out.UserQueryPort;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProjectService implements UpdateProjectUseCase {

    private final UpdateProjectPort port;
    private final ProjectValidator projectValidator;
    private final AssignmentQueryPort assignmentQueryPort;
    private final SaveProjectUserPort saveProjectUserPort;
    private final UserQueryPort userQueryPort;
    private final DeleteAssignmentPort deleteAssignmentPort;


    private final ReadProjectPort readProjectPort;


    // todo : 사용자 인증 로직 추가
    // PR 작성 : 수정 시 새로 등록된 유저의 경우 updateService 에서 create 해주는 게 맞는건지 의문
    @Override
    @Transactional
    public void updateProject(Long projectId,
        ProjectUpdateCommand command) {
        Project project = projectValidator.findProjectById(projectId);
        project.update(command);
        port.update(project);

        // 업체 타입 별 기존 assignments
        List<Assignment> oldDevs = assignmentQueryPort.findUsersByProjectId(projectId)
            .stream().filter(assignment -> assignment.getCompanyType() == CompanyType.DEVELOPER)
            .toList();
        List<Assignment> oldClients = assignmentQueryPort.findUsersByProjectId(projectId)
            .stream().filter(assignment -> assignment.getCompanyType() == CompanyType.CLIENT)
            .toList();

        // request 로 받아온 변경된 users ( 변경 + 미변경 user 모두 포함 )
        // 담당자 리스트
        List<User> devManagers = command.getDevManagerId().stream()
            .map(userQueryPort::findById)
            .toList();
        List<User> clientManagers = command.getClientManagerId().stream()
            .map(userQueryPort::findById).toList();
        // 멤버 리스트
        List<User> devUsers = command.getDevUserId().stream()
            .map(userQueryPort::findById).toList();
        List<User> clientUsers = command.getClientUserId().stream()
            .map(userQueryPort::findById).toList();

        updateAssignments(oldDevs, devUsers, devManagers);
        updateAssignments(oldClients, clientUsers, clientManagers);

        newAssignUsers(project, oldDevs, oldClients, devManagers, devUsers,
            clientManagers, clientUsers);
    }

    @Override
    public void changedStatus(Long projectId) {
        Project project = readProjectPort.findProjectById(projectId);

        if (project.getStatus() == ProjectStatus.IN_PROGRESS) {
            project.statusCompleted();
        } else {
            project.statusInProgress();
        }
        port.update(project);
    }

    private void updateAssignments(List<Assignment> oldAssignments, List<User> updatedUsers,
        List<User> updatedManagers) {
        // Set 으로 했어야하는구만
        // 변경된 userId 집합
        Set<Long> updatedUserIds = updatedUsers.stream().map(User::getId)
            .collect(Collectors.toSet());
        // 변경된 manager userId 집합
        Set<Long> managerIds = updatedManagers.stream().map(User::getId)
            .collect(Collectors.toSet());

        // 기존 멤버의 아이디와 변경된 아이디가 같으면 각 deManagers의 id 중 devUsers의 id와 하나라도 일치하면 userType MANAGER로 변경, 일치하지 않으면 MEMBER로 변경
        oldAssignments.stream()
            .filter(assignment -> updatedUserIds.contains(assignment.getUser().getId())) // 변경 유저아이디에 기존 아이디가 포함되어 있으면 내용 변경
            .forEach(assignment -> {
                UserType newUserType =
                    managerIds.contains(assignment.getUser().getId()) ? UserType.MANAGER
                        : UserType.MEMBER;
                assignment.updateUserType(newUserType);
            });

        // todo: 포함안되면 삭제
        List<Assignment> deleteUsers = oldAssignments.stream()
            .filter(assignment -> !updatedUserIds.contains(assignment.getUser().getId()))
            .toList();
        deleteAssignmentPort.DeleteAllInBatch(deleteUsers);
    }

    private void newAssignUsers(Project project, List<Assignment> oldDevs,
        List<Assignment> oldClients, List<User> devManagers, List<User> devUsers,
        List<User> clientManagers, List<User> clientUsers) {

        // 기존 userId 리스트
        List<Long> oldDevUserIds = oldDevs.stream().map(assignment -> assignment.getUser().getId())
            .toList();
        List<Long> oldClientUserIds = oldClients.stream()
            .map(assignment -> assignment.getUser().getId()).toList();

        // 새로 추가된 user 리스트 - 기존 userId 에 포함되어 있지 않은 user 리스트
        List<User> newDevUsers = devUsers.stream()
            .filter(user -> !oldDevUserIds.contains(user.getId()))
            .toList();
        List<User> newClientUsers = clientUsers.stream()
            .filter(user -> !oldClientUserIds.contains(user.getId()))
            .toList();

        // 생성
        List<Assignment> newAssignments = Assignment.createProjectUser(
            project,
            devManagers,
            clientManagers,
            newDevUsers,
            newClientUsers
        );

        saveProjectUserPort.saveAll(newAssignments);
    }
}
