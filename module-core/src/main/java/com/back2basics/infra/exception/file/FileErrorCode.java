package com.back2basics.infra.exception.file;


import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FileErrorCode implements ErrorCode {

    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "F001", "파일을 찾을 수 없습니다."),
    FILE_DOWNLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "F002", "파일 다운로드 실패");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
