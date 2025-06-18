package com.back2basics.inquiry.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum InquiryStatus {

    WAITING,
    ANSWERED
}
