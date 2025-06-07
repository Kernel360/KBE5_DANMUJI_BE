package com.back2basics.adapter.persistence.user.adapter.out;

import static com.back2basics.infra.exception.user.UserErrorCode.USER_NOT_FOUND;

import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserQueryAdapter implements UserQueryPort {

    private final UserMapper userMapper;
    private final UserEntityRepository userEntityRepository;

    @Override
    public User findByUsername(String username) {
        return userEntityRepository.findByUsername(username).map(userMapper::toDomain)
            .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }

    @Override
    public User findById(Long userId) {
        return userEntityRepository.findById(userId).map(userMapper::toDomain)
            .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userEntityRepository.existsByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userEntityRepository.findAll().stream().map(userMapper::toDomain).toList();
    }

    @Override
    public List<User> findAllByCompanyId(Long companyId) {
        return userEntityRepository.findAllByCompany_IdAndDeletedAtIsNull(companyId)
            .stream().map(userMapper::toDomain).toList();
    }

}
