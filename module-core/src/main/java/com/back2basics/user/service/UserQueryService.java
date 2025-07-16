package com.back2basics.user.service;

import com.back2basics.company.model.Company;
import com.back2basics.infra.validator.CompanyValidator;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.port.in.command.SearchUserCommand;
import com.back2basics.user.port.out.UserQueryPort;
import com.back2basics.user.service.result.UserInfoResult;
import com.back2basics.user.service.result.UserPositionResult;
import com.back2basics.user.service.result.UserSimpleResult;
import java.util.List;
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
    public List<UserInfoResult> getAll() {
        return userQueryPort.findAllByDeletedAtIsNull().stream().map(user -> {
            Company company = user.getCompanyId() != null
                ? companyValidator.findCompany(user.getCompanyId())
                : null;
            return UserInfoResult.of(user, company);
        }).toList();
    }

    @Override
    public List<UserPositionResult> getAllPositions() {
        return userQueryPort.findAllPositions().stream().map(UserPositionResult::from).toList();
    }

    @Override
    public Page<UserInfoResult> getAllUsers(Pageable pageable) {
        return userQueryPort.findAllByDeletedAtIsNull(pageable)
            .map(user -> {
                Company company = user.getCompanyId() != null
                    ? companyValidator.findCompany(user.getCompanyId())
                    : null;
                return UserInfoResult.of(user, company);
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
    public Long getUserCounts() {
        return userQueryPort.getUserCounts();
    }

    @Override
    public Page<UserInfoResult> searchUsers(SearchUserCommand command, Pageable pageable) {
        return userQueryPort.searchUsers(command, pageable)
            .map(user -> {
                Company company = user.getCompanyId() != null
                    ? companyValidator.findCompany(user.getCompanyId())
                    : null;
                return UserInfoResult.of(user, company);
            });
    }
}
