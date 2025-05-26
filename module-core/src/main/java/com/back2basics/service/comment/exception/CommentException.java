package com.back2basics.service.comment.exception;

import com.back2basics.response.global.error.CustomException;

public class CommentException extends CustomException {

    public CommentException(CommentErrorCode errorCode) {
        super(errorCode);
    }
}
