package com.back2basics.adapter.persistence.user.adapter.out;

import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserRepositoryPort;
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
