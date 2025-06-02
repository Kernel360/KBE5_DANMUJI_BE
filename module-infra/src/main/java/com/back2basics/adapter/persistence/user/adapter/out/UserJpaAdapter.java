package com.back2basics.adapter.persistence.user.adapter.out;

import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.user.UserErrorCode;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public User findById(Long userId) {
        return userEntityRepository.findById(userId).map(userMapper::toDomain)
            .orElseThrow(() -> new UserException(
                UserErrorCode.USER_NOT_FOUND));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userEntityRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        UserEntity userEntity = userEntityRepository.findById(userId)
            .orElseThrow(() -> new UserException(
                UserErrorCode.USER_NOT_FOUND));
        userEntity.markDeleted();
    }

}
