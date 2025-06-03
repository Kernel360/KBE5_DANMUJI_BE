package com.back2basics.infra.exception.comment;

import com.back2basics.global.response.error.CustomException;

public class CommentException extends CustomException {

    public CommentException(CommentErrorCode errorCode) {
        super(errorCode);
    }
}
