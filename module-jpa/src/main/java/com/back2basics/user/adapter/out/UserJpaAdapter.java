package com.back2basics.user.adapter.out;

import com.back2basics.model.user.User;
import com.back2basics.port.out.user.UserRepositoryPort;
import com.back2basics.user.entity.UserEntity;
import com.back2basics.user.mapper.UserMapper;
import com.back2basics.user.repository.UserEntityRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserEntityRepository userEntityRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        userEntityRepository.save(entity);
        return userMapper.toDomain(entity);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userEntityRepository.findById(userId).map(userMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userEntityRepository.existsByUsername(username);
    }

}
