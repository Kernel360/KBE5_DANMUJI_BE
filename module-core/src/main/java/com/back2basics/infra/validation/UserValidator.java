package com.back2basics.infra.validation;

import com.back2basics.model.user.User;
import com.back2basics.port.out.user.UserRepositoryPort;
import com.back2basics.service.user.exception.UserErrorCode;
import com.back2basics.service.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

	private final UserRepositoryPort userRepositoryPort;

	public User findUserById(Long userId) {
		return userRepositoryPort.findById(userId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
	}
}
