package com.back2basics.service.project.exception;

import com.back2basics.response.global.error.CustomException;

public class ProjectException extends CustomException {
    public ProjectException(ProjectErrorCode errorCode) {
        super(errorCode);
    }
}
