package com.back2basics.response.global;

public interface BaseResponseCode {

    int getStatus();
    String getCode();
    String getMessage();
}