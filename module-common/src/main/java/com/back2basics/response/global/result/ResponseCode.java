package com.back2basics.response.global.result;

public interface ResponseCode {

    int getStatus();
    String getCode();
    String getMessage();
}