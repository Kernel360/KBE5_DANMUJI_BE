package com.back2basics.service.project.exception;

import com.back2basics.global.response.error.CustomException;

public class ProjectException extends CustomException {

    public ProjectException(ProjectErrorCode errorCode) {
        super(errorCode);
    }
}
