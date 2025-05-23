package com.back2basics.service.user;

import com.back2basics.model.user.User;
import com.back2basics.port.in.user.CreateUserUseCase;
import com.back2basics.port.out.user.UserRepositoryPort;
import com.back2basics.service.user.dto.UserCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CreateUserUseCase {

  private final UserRepositoryPort userRepositoryPort;

  @Override
  public Long createUser(UserCreateCommand command) {
    User user = User.builder()
        .username(command.getUsername())
        .password(command.getPassword())
        .build();
    return userRepositoryPort.save(user);
  }
}
