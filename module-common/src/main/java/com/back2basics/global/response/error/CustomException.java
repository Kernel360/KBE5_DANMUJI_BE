package com.back2basics.global.response.error;

import com.back2basics.global.response.code.ErrorCode;
import com.back2basics.global.response.error.ErrorResponse.FieldError;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private List<FieldError> errors = new ArrayList<>();

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errors = errors;
    }
}