package com.back2basics.inquiry.port.out;

import com.back2basics.inquiry.model.Inquiry;

public interface DeleteInquiryPort {

    Inquiry softDelete(Inquiry inquiry);

}
