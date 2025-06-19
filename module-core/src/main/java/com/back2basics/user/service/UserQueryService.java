package com.back2basics.user.service;

import com.back2basics.company.model.Company;
import com.back2basics.infra.validation.validator.CompanyValidator;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.port.out.UserQueryPort;
import com.back2basics.user.service.result.UserInfoResult;
import com.back2basics.user.service.result.UserSimpleResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService implements UserQueryUseCase {

    private final UserQueryPort userQueryPort;
    private final CompanyValidator companyValidator;

    @Override
    public UserInfoResult getUserInfo(Long userId) {
        User user = userQueryPort.findById(userId);
        Company company = user.getCompanyId() != null
            ? companyValidator.findCompany(user.getCompanyId())
            : null;

        return UserInfoResult.of(user, company);
    }

    @Override
    public Page<UserSimpleResult> getAllUsers(Pageable pageable) {
        return userQueryPort.findAllByDeletedAtIsNull(pageable)
            .map(user -> {
                Company company = user.getCompanyId() != null
                    ? companyValidator.findCompany(user.getCompanyId())
                    : null;
                return UserSimpleResult.of(user, company);
            });
    }

    @Override
    public boolean existsByUsername(String username) {
        return userQueryPort.existsByUsername(username);
    }

    @Override
    public Page<UserSimpleResult> getDeletedUsers(Pageable pageable) {
        return userQueryPort.findAllByDeletedAtIsNotNull(pageable)
            .map(user -> {
                Company company = user.getCompanyId() != null
                    ? companyValidator.findCompany(user.getCompanyId())
                    : null;
                return UserSimpleResult.of(user, company);
            });
    }

    @Override
    public Map<Long, String> getNameByIds(List<Long> userIds) {
        return userQueryPort.findByIds(userIds).stream()
            .collect(Collectors.toMap(User::getId, User::getName));
    }
}
