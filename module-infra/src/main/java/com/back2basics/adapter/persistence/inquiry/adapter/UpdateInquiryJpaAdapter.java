package com.back2basics.adapter.persistence.inquiry.adapter;

import com.back2basics.adapter.persistence.inquiry.InquiryEntityRepository;
import com.back2basics.adapter.persistence.inquiry.InquiryMapper;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.out.UpdateInquiryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateInquiryJpaAdapter implements UpdateInquiryPort {

    private final InquiryEntityRepository inquiryEntityRepository;
    private final InquiryMapper inquiryMapper;

    @Override
    public void update(Inquiry inquiry) {
        inquiryEntityRepository.save(inquiryMapper.toEntity(inquiry));
    }

}
