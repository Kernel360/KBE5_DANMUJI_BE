package com.back2basics.port.in.user;

import com.back2basics.service.user.command.UserCreateCommand;
import com.back2basics.service.user.result.UserCreateResult;

public interface CreateUserUseCase {

    UserCreateResult createUser(UserCreateCommand command);
}
