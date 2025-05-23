package com.back2basics.port.in.user;

import com.back2basics.service.user.dto.UserCreateCommand;

public interface CreateUserUseCase {

  void createUser(UserCreateCommand command);
}
