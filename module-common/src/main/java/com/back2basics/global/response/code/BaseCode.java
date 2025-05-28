package com.back2basics.global.response.code;

import org.springframework.http.HttpStatus;

public interface BaseCode {

    HttpStatus getStatus();

    String getCode();

    String getMessage();
}