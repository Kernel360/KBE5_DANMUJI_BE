package com.back2basics.global.response.result;

import com.back2basics.global.response.code.BaseCode;
import com.back2basics.global.response.code.ErrorCode;
import com.back2basics.global.response.code.ResponseCode;
import com.back2basics.global.response.error.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> {

    private final HttpStatus status;
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final T data;

    public ApiResponse(BaseCode baseCode, T data) {
        this.status = baseCode.getStatus();
        this.code = baseCode.getCode();
        this.message = baseCode.getMessage();
        this.data = data;
    }

    public ApiResponse(BaseCode baseCode) {
        this(baseCode, null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(
        ResponseCode responseCode, T data) {
        return ResponseEntity
            .status(responseCode.getStatus())
            .body(new ApiResponse<>(responseCode, data));
    }

    public static ResponseEntity<ApiResponse<Void>> success(
        ResponseCode responseCode) {
        return ResponseEntity
            .status(responseCode.getStatus())
            .body(new ApiResponse<>(responseCode));
    }

    public static ResponseEntity<ApiResponse<ErrorResponse>> error(ErrorCode errorCode) {
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(new ApiResponse<>(errorCode));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(ErrorCode errorCode, T errorDetails) {
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(new ApiResponse<>(errorCode, errorDetails));
    }
}