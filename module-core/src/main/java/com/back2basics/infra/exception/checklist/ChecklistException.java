package com.back2basics.infra.exception.checklist;

import com.back2basics.global.response.error.CustomException;

public class ChecklistException extends CustomException {

    public ChecklistException(ChecklistErrorCode errorCode) {
        super(errorCode);
    }
}
