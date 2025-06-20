package com.back2basics.infra.exception.projectstep;

import com.back2basics.global.response.error.CustomException;


public class ProjectStepException extends CustomException {

    public ProjectStepException(ProjectStepErrorCode errorCode) {
        super(errorCode);
    }
}
