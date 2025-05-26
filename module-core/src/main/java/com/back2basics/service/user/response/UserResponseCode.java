package com.back2basics.service.user.response;

import com.back2basics.response.global.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserResponseCode implements ResponseCode {

    USER_CREATE_SUCCESS(HttpStatus.CREATED, "U201", "사용자 생성 완료"),
    USER_READ_SUCCESS(HttpStatus.OK, "U202", "사용자 조회 완료"),
    //    USER_READ_ALL_SUCCESS(HttpStatus.OK, "U203", "사용자 목록 조회 완료"),
    USER_UPDATE_SUCCESS(HttpStatus.OK, "U204", "사용자 수정 완료"),
    USER_DELETE_SUCCESS(HttpStatus.OK, "U205", "사용자 삭제 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
