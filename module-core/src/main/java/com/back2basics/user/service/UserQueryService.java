package com.back2basics.user.service;

import com.back2basics.company.model.Company;
import com.back2basics.infra.validation.validator.CompanyValidator;
import com.back2basics.user.model.Role;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.port.out.UserQueryPort;
import com.back2basics.user.service.result.UserInfoResult;
import com.back2basics.user.service.result.UserSimpleResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService implements UserQueryUseCase {

    private final UserQueryPort userQueryPort;
    private final CompanyValidator companyValidator;

    @Override
    public UserInfoResult getUserInfo(Long userId) {
        User user = userQueryPort.findById(userId);
        if (user.getCompanyId() == null) {
            return new UserInfoResult(userId, user.getUsername(), user.getName(), user.getEmail(),
                user.getPhone(), user.getPosition(), user.getUserType(), null, null,
                user.getCreatedAt(), user.getUpdatedAt());
        }
        Company company = companyValidator.findCompany(user.getCompanyId());
        return new UserInfoResult(userId, user.getUsername(), user.getName(), user.getEmail(),
            user.getPhone(), user.getPosition(), user.getUserType(), user.getCompanyId(),
            company.getName(), user.getCreatedAt(), user.getUpdatedAt());
    }

    @Override
    public List<UserSimpleResult> getAllUsers() {
        return userQueryPort.findAll().stream()
            .filter(user -> !user.getRole().equals(Role.ADMIN))
            .map(user -> {
                if (user.getCompanyId() == null) {
                    return new UserSimpleResult(
                        user.getId(),
                        user.getUsername(),
                        user.getName(),
                        user.getPhone(),
                        user.getPosition(),
                        null,
                        null
                    );
                }
                Company company = companyValidator.findCompany(user.getCompanyId());
                return new UserSimpleResult(
                    user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getPhone(),
                    user.getPosition(),
                    user.getCompanyId(),
                    company.getName()
                );
            })
            .toList();
    }

}
