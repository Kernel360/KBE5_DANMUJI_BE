package com.back2basics.user.service;

import com.back2basics.company.model.Company;
import com.back2basics.infra.validation.validator.CompanyValidator;
import com.back2basics.user.model.User;
import com.back2basics.user.port.in.UpdateUserUseCase;
import com.back2basics.user.port.in.command.UserUpdateCommand;
import com.back2basics.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final CompanyValidator companyValidator;

    @Override
    public void update(Long userId, UserUpdateCommand command) {
        User user = userRepositoryPort.findById(userId);
        Company company = companyValidator.findCompany(command.getCompanyId());
        user.updateUser(command.getUsername(), command.getName(), command.getEmail(),
            command.getPhone(), command.getPosition(), company);

        userRepositoryPort.save(user);
    }

}
