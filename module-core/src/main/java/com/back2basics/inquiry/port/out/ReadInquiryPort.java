package com.back2basics.inquiry.port.out;

import com.back2basics.inquiry.model.Inquiry;
import java.util.List;

public interface ReadInquiryPort {

    Inquiry getInquiry(Long id);

    List<Inquiry> getAllInquiries();

}
