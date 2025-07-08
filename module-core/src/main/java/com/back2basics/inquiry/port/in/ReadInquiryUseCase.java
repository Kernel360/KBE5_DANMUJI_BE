package com.back2basics.inquiry.port.in;

import com.back2basics.inquiry.model.InquirySearchCondition;
import com.back2basics.inquiry.service.result.CountInquiryResult;
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

    CountInquiryResult getInquiryCounts();

    List<ReadRecentInquiryResult> getRecentInquiries();

    Page<ReadInquiryResult> searchInquiries(InquirySearchCondition condition, Pageable pageable);
}
