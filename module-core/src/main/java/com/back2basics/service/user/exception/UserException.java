package com.back2basics.service.user.exception;

import com.back2basics.response.global.error.CustomException;

public class UserException extends CustomException {

	public UserException(UserErrorCode errorCode) {
		super(errorCode);
	}
}
