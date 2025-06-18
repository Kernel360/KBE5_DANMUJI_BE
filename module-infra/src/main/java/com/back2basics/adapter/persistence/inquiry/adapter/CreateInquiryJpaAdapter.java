package com.back2basics.adapter.persistence.inquiry.adapter;

import com.back2basics.adapter.persistence.inquiry.InquiryEntity;
import com.back2basics.adapter.persistence.inquiry.InquiryEntityRepository;
import com.back2basics.adapter.persistence.inquiry.InquiryMapper;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.out.CreateInquiryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateInquiryJpaAdapter implements CreateInquiryPort {

    private final InquiryEntityRepository inquiryEntityRepository;
    private final InquiryMapper inquiryMapper;

    @Override
    public Long save(Inquiry inquiry) {
        InquiryEntity entity = inquiryMapper.toEntity(inquiry);
        return inquiryEntityRepository.save(entity).getId();
    }

}
