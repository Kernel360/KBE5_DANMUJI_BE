package com.back2basics.infra.exception.project;

import com.back2basics.global.response.error.CustomException;

public class ProjectException extends CustomException {

    public ProjectException(ProjectErrorCode errorCode) {
        super(errorCode);
    }
}
