package com.back2basics.userByProject.service;

import com.back2basics.project.port.in.command.ProjectCreateCommand;
import com.back2basics.userByProject.model.UserByProject;
import com.back2basics.userByProject.port.in.CreateUserProjectUseCase;
import com.back2basics.userByProject.port.in.command.CreateUserProjectCommand;
import com.back2basics.userByProject.port.out.SaveUserProjectPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserProjectService implements CreateUserProjectUseCase {

    private final SaveUserProjectPort port;

    @Override
    public void createUserByProject(List<CreateUserProjectCommand> commands) {


    }
}
