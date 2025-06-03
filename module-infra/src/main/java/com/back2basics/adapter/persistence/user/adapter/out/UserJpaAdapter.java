package com.back2basics.adapter.persistence.user.adapter.out;

import static com.back2basics.infra.exception.company.CompanyErrorCode.COMPANY_NOT_FOUND;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.company.CompanyException;
import com.back2basics.infra.exception.user.UserErrorCode;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserRepositoryPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserEntityRepository userEntityRepository;
    private final CompanyEntityRepository companyEntityRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        CompanyEntity companyEntity = null;
        if (user.getCompanyId() != null ) {
            companyEntity = companyEntityRepository.findById(user.getCompanyId())
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));
        }
        UserEntity entity = userMapper.toEntity(user, companyEntity);
        return userMapper.toDomain(userEntityRepository.save(entity));
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

    @Override
    public Optional<User> findByUsername(String username) {
        return userEntityRepository.findByUsername(username)
            .map(userMapper::toDomain);
    }

}
