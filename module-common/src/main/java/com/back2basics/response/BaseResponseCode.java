package com.back2basics.response;

public interface BaseResponseCode {

    int getStatus();
    String getCode();
    String getMessage();
}