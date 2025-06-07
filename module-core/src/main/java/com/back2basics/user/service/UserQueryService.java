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
        Company company = user.getCompanyId() != null
            ? companyValidator.findCompany(user.getCompanyId())
            : null;

        return UserInfoResult.of(user, company);
    }

    @Override
    public List<UserSimpleResult> getAllUsers() {
        return userQueryPort.findAll().stream()
            .filter(user -> user.getRole() != Role.ADMIN)
            .map(user -> {
                Company company = user.getCompanyId() != null
                    ? companyValidator.findCompany(user.getCompanyId())
                    : null;
                return UserSimpleResult.of(user, company);
            })
            .toList();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userQueryPort.existsByUsername(username);
    }

}
