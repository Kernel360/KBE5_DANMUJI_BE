package com.back2basics.inquiry.port.in;

import com.back2basics.inquiry.port.in.command.InquirySearchCommand;
import com.back2basics.inquiry.service.result.InquirySummaryResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchInquiryUseCase {
    Page<InquirySummaryResult> searchWithFilter(InquirySearchCommand command, Pageable pageable);
}
