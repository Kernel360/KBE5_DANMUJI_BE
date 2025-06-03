package com.back2basics.infra.exception.projectstep;

import com.back2basics.global.response.code.ErrorCode;
import com.back2basics.global.response.error.CustomException;
import com.back2basics.infra.exception.project.ProjectErrorCode;
import com.back2basics.infra.exception.project.ProjectException;

public class ProjectStepException extends CustomException {

    public ProjectStepException(ErrorCode errorCode) {
        super(errorCode);
    }
}
