package com.back2basics.userByProject.port.in;

import com.back2basics.userByProject.port.in.command.CreateUserProjectCommand;
import java.util.List;

public interface CreateUserProjectUseCase {

    void createUserByProject(List<CreateUserProjectCommand> commands);
}
