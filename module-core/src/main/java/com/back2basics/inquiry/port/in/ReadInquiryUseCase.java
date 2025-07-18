package com.back2basics.inquiry.port.in;

import com.back2basics.inquiry.port.in.command.InquirySearchCommand;
import com.back2basics.inquiry.service.result.CountInquiryResult;
import com.back2basics.inquiry.service.result.ReadInquiryResult;
import com.back2basics.inquiry.service.result.ReadRecentInquiryResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadInquiryUseCase {

    ReadInquiryResult getInquiry(Long id, Long userId);

    ReadInquiryResult getInquiryAsAdmin(Long id);

    List<ReadInquiryResult> getAllInquiries();

    Page<ReadInquiryResult> getAllInquiries(Pageable pageable);

    Page<ReadInquiryResult> getMyInquiries(Pageable pageable, Long id);

    CountInquiryResult getInquiryCounts();

    List<ReadRecentInquiryResult> getRecentInquiries();

    Page<ReadInquiryResult> searchInquiries(InquirySearchCommand command, Pageable pageable);

    Page<ReadInquiryResult> searchUserInquiries(Long userId, InquirySearchCommand command,
        Pageable pageable);
}
