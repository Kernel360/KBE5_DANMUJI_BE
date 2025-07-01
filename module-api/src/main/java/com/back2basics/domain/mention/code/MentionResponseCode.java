package com.back2basics.domain.mention.code;

import com.back2basics.global.response.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MentionResponseCode implements ResponseCode {

    MENTION_READ_ALL_SUCCESS(HttpStatus.OK, "MT200", "멘션 목록 조회 완료");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
