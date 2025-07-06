package com.back2basics.inquiry.port.out;

import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.model.InquiryCountsDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadInquiryPort {

    Optional<Inquiry> getInquiry(Long id);

    List<Inquiry> getAllInquiries();

    Page<Inquiry> getAllInquiries(Pageable pageable);

    Page<Inquiry> getMyInquiries(Pageable pageable, Long id);

    InquiryCountsDto getInquiryCounts();

    List<Inquiry> getRecentInquiries();

}
