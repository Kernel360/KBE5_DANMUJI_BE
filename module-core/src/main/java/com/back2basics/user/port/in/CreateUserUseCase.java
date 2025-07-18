package com.back2basics.user.port.in;

import com.back2basics.user.port.in.command.UserCreateCommand;
import com.back2basics.user.service.result.UserCreateResult;

public interface CreateUserUseCase {

    UserCreateResult create(UserCreateCommand command, Long loggedInUserId);

    UserCreateResult create(UserCreateCommand command);
}
