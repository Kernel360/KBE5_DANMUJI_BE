package com.back2basics.response.global.code;

import org.springframework.http.HttpStatus;

public interface BaseCode {

  HttpStatus getStatus();

  String getCode();

  String getMessage();
}