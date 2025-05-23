package com.back2basics.service.post.exception;

import com.back2basics.response.global.error.CustomException;
import com.back2basics.response.global.error.ErrorResponse.FieldError;
import java.util.List;

public class PostException extends CustomException {

	public PostException(PostErrorCode errorCode) {
		super(errorCode);
	}

	public PostException(PostErrorCode errorCode, List<FieldError> errors) {
		super(errorCode, errors);
	}
}
