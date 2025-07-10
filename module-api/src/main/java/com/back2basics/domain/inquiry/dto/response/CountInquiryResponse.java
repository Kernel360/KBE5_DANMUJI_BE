package com.back2basics.domain.inquiry.dto.response;

import com.back2basics.inquiry.service.result.CountInquiryResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountInquiryResponse {

    private Long total;
    private Long waitingCnt;
    private Long answeredCnt;

    public static CountInquiryResponse toResponse(CountInquiryResult result) {
        return CountInquiryResponse.builder()
            .total(result.total())
            .waitingCnt(result.waitingCnt())
            .answeredCnt(result.answeredCnt())
            .build();
    }
}
