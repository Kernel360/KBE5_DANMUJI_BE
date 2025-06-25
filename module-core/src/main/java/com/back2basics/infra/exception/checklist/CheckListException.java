package com.back2basics.infra.exception.checklist;

import com.back2basics.global.response.error.CustomException;

public class CheckListException extends CustomException {

    public CheckListException(CheckListErrorCode errorCode) {
        super(errorCode);
    }
}
