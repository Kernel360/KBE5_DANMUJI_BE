package com.back2basics.infra.exception.file;


import com.back2basics.global.response.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FileErrorCode implements ErrorCode {

    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "F001", "파일을 찾을 수 없습니다."),
    FILE_DOWNLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "F002", "파일 다운로드 실패"),
    FILE_DOWNLOAD_DENIED(HttpStatus.FORBIDDEN, "F003", "파일 다운로드 권한이 없습니다."),
    FILE_DELETE_FAILED_URL_EMPTY(HttpStatus.BAD_REQUEST, "F004", "파일 URL이 null이거나 비어 있습니다."),
    FILE_DELETE_FAILED_WRONG_URL(HttpStatus.BAD_REQUEST, "F004", "파일 URL이 잘못된 형식입니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "C009", "업로드 가능한 파일 크기를 초과했습니다 (20MB까지 가능)");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
