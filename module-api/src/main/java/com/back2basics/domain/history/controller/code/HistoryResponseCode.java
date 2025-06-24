package com.back2basics.domain.history.controller.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HistoryResponseCode implements ResponseCode {

    HISTORY_READ_ALL_SUCCESS(HttpStatus.OK, "H200", "이력 목록 전체 조회 완료"),
    HISTORY_READ_SUCCESS(HttpStatus.OK, "H201", "이력 단건 조회 완료"),
    HISTORY_SEARCH_SUCCESS(HttpStatus.OK, "H202", "이력 검색 목록 조회 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}