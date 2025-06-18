package com.back2basics.inquiry.service;

import com.back2basics.inquiry.port.in.DeleteInquiryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteInquiryService implements DeleteInquiryUseCase {

    @Override
    public void deleteInquiry(Long id) {

    }

}
