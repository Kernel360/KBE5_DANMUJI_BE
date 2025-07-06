package com.back2basics.inquiry.port.in;

import com.back2basics.inquiry.model.InquiryCountsDto;
import com.back2basics.inquiry.service.result.ReadInquiryResult;
import com.back2basics.inquiry.service.result.ReadRecentInquiryResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadInquiryUseCase {

    ReadInquiryResult getInquiry(Long id);

    List<ReadInquiryResult> getAllInquiries();

    Page<ReadInquiryResult> getAllInquiries(Pageable pageable);

    Page<ReadInquiryResult> getMyInquiries(Pageable pageable, Long id);

    InquiryCountsDto getInquiryCounts();

    List<ReadRecentInquiryResult> getRecentInquiries();

}
