package com.back2basics.inquiry.service;

import com.back2basics.inquiry.port.in.SearchInquiryUseCase;
import com.back2basics.inquiry.port.in.command.InquirySearchCommand;
import com.back2basics.inquiry.port.out.SearchInquiryPort;
import com.back2basics.inquiry.service.result.InquirySummaryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchInquiryService implements SearchInquiryUseCase {

    private final SearchInquiryPort searchInquiryPort;

    @Override
    public Page<InquirySummaryResult> searchWithFilter(InquirySearchCommand command, Pageable pageable) {
        return searchInquiryPort.searchWithFilter(command, pageable)
            .map(InquirySummaryResult::toResult);
    }
}
