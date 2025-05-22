package com.back2basics.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface ErrorCode {
    int getStatus();
    String getCode();
    String getMessage();
}