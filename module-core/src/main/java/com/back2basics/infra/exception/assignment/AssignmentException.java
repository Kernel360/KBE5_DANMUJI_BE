package com.back2basics.infra.exception.assignment;

import com.back2basics.global.response.error.CustomException;

public class AssignmentException extends CustomException {

    public AssignmentException(AssignmentErrorCode errorCode) {
        super(errorCode);
    }
}
