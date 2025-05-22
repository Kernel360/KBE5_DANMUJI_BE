package com.back2basics.response.global;

public interface ResponseCode {

    int getStatus();
    String getCode();
    String getMessage();
}