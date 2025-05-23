package com.back2basics.user.adapter.out;

import com.back2basics.model.user.User;
import com.back2basics.port.out.user.UserRepositoryPort;
import com.back2basics.user.entity.UserEntity;
import com.back2basics.user.mapper.UserMapper;
import com.back2basics.user.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepositoryPort {

  private final UserEntityRepository userEntityRepository;
  private final UserMapper userMapper;

  @Override
  public Long save(User user) {
    UserEntity entity = userMapper.toEntity(user);
    return userEntityRepository.save(entity).getId();
  }
}
