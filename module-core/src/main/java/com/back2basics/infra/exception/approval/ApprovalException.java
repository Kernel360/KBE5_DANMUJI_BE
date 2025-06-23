package com.back2basics.infra.exception.approval;

import com.back2basics.global.response.error.CustomException;

public class ApprovalException extends CustomException {

    public ApprovalException(ApprovalErrorCode errorCode) {
        super(errorCode);
    }
}
