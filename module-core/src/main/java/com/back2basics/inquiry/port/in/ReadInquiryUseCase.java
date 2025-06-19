package com.back2basics.inquiry.port.in;

import com.back2basics.inquiry.service.result.ReadInquiryResult;
import java.util.List;

public interface ReadInquiryUseCase {

    ReadInquiryResult getInquiry(Long id);

    List<ReadInquiryResult> getAllInquiries();

}
