package com.back2basics.adapter.persistence.user.adapter.out;

import static com.back2basics.infra.exception.user.UserErrorCode.USER_NOT_FOUND;

import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<User> findByIds(List<Long> ids) {
        return userEntityRepository.findAllById(ids).stream()
            .map(userMapper::toDomain).toList();
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
    public List<User> findAllByDeletedAtIsNull() {
        return userEntityRepository.findAllByDeletedAtIsNull().stream().map(userMapper::toDomain)
            .toList();
    }

    @Override
    public Page<User> findAllByDeletedAtIsNull(Pageable pageable) {
        return userEntityRepository.findAllByDeletedAtIsNull(pageable)
            .map(userMapper::toDomain);
    }

    @Override
    public Page<User> findAllByDeletedAtIsNotNull(Pageable pageable) {
        return userEntityRepository.findAllByDeletedAtIsNotNull(pageable)
            .map(userMapper::toDomain);
    }

    @Override
    public List<User> findAllByCompanyId(Long companyId) {
        return userEntityRepository.findAllByCompany_IdAndDeletedAtIsNull(companyId)
            .stream().map(userMapper::toDomain).toList();
    }

    @Override
    public List<User> findAllByCompanyIdAndDeletedAtIsNull(Long companyId) {
        return userEntityRepository.findAllByCompany_IdAndDeletedAtIsNull(companyId)
            .stream().map(userMapper::toDomain).toList();
    }

    @Override
    public boolean existsById(Long userId) {
        return userEntityRepository.existsById(userId);
    }

    @Override
    public void saveAll(List<User> users) {
        List<UserEntity> userEntities = users.stream()
            .map(userMapper::toEntity)  // User → UserEntity 매핑
            .collect(Collectors.toList());

        userEntityRepository.saveAll(userEntities);
    }

    @Override
    public List<User> findAllByIds(List<Long> userIds) {
        return userEntityRepository.findAllById(userIds)
            .stream().map(userMapper::toDomain).toList();
    }

    @Override
    public Map<String, Long> findUserIdsByUsernames(List<String> usernames) {
        return userEntityRepository.findAllByUsernames(usernames).stream()
            .collect(Collectors.toMap(UserEntity::getUsername, UserEntity::getId));
    }
}
