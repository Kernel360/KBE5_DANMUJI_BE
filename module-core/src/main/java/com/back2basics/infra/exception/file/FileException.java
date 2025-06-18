package com.back2basics.infra.exception.file;

import com.back2basics.global.response.error.CustomException;

public class FileException extends CustomException {

    public FileException(FileErrorCode errorCode) {
        super(errorCode);
    }

}

