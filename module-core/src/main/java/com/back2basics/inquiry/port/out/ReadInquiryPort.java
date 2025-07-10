package com.back2basics.inquiry.port.out;

import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.in.command.InquirySearchCommand;
import com.back2basics.inquiry.service.result.CountInquiryResult;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadInquiryPort {

    Optional<Inquiry> getInquiry(Long id, Long userId);

    Optional<Inquiry> getUsersInquiry(Long id);

    List<Inquiry> getAllInquiries();

    Page<Inquiry> getAllInquiries(Pageable pageable);

    Page<Inquiry> getMyInquiries(Pageable pageable, Long id);

    CountInquiryResult getInquiryCounts();

    List<Inquiry> getRecentInquiries();

    Page<Inquiry> search(InquirySearchCommand condition, Pageable pageable);

}
