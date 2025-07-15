package com.back2basics.inquiry.port.out;

import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.in.command.InquirySearchCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchInquiryPort {
    Page<Inquiry> searchWithFilter(InquirySearchCommand command, Pageable pageable);
}