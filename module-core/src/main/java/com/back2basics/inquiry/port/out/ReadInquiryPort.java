package com.back2basics.inquiry.port.out;

import com.back2basics.inquiry.model.Inquiry;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadInquiryPort {

    Inquiry getInquiry(Long id);

    List<Inquiry> getAllInquiries();

    Page<Inquiry> getAllInquiries(Pageable pageable);

}
