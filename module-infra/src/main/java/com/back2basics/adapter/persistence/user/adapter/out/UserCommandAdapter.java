package com.back2basics.adapter.persistence.user.adapter.out;

import static com.back2basics.infra.exception.company.CompanyErrorCode.COMPANY_NOT_FOUND;
import static com.back2basics.infra.exception.user.UserErrorCode.USER_NOT_FOUND;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.company.CompanyException;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserCommandPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class UserCommandAdapter implements UserCommandPort {

    private final UserEntityRepository userEntityRepository;
    private final CompanyEntityRepository companyEntityRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        CompanyEntity companyEntity = null;
        if (user.getCompanyId() != null) {
            companyEntity = companyEntityRepository.findById(user.getCompanyId())
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));
        }
        UserEntity entity = userMapper.toEntity(user, companyEntity);
        return userMapper.toDomain(userEntityRepository.save(entity));
    }

    @Override
    public void deleteById(Long userId) {
        UserEntity userEntity = userEntityRepository.findById(userId)
            .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        userEntity.markDeleted();
    }

    @Override
    public void softDeleteByCompanyId(Long companyId) {
        List<UserEntity> users = userEntityRepository.findAllByCompany_IdAndDeletedAtIsNull(
            companyId);
        for (UserEntity userEntity : users) {
            userEntity.markDeleted();
        }
    }
}
