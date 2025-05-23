package com.back2basics.response.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "internal server error"),
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "Invalid input type"),
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "Method not allowed"),
  INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", "Invalid type value"),
  BAD_CREDENTIALS(HttpStatus.BAD_REQUEST, "C005", "Invalid username or password"),
  NOT_FOUND(HttpStatus.NOT_FOUND, "C006", "Resource not found");

  private final HttpStatus status;
  private final String code;
  private final String message;
}