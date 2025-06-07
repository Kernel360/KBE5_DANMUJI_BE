package com.back2basics.infra.exception.post;

import com.back2basics.global.response.error.CustomException;
import com.back2basics.global.response.error.ErrorResponse.FieldError;
import java.util.List;

public class PostException extends CustomException {

    public PostException(PostErrorCode errorCode) {
        super(errorCode);
    }

    public PostException(PostErrorCode errorCode, List<FieldError> errors) {
        super(errorCode, errors);
    }
}
