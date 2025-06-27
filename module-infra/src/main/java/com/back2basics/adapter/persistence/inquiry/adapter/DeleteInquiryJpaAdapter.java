package com.back2basics.adapter.persistence.inquiry.adapter;

import com.back2basics.adapter.persistence.inquiry.InquiryEntity;
import com.back2basics.adapter.persistence.inquiry.InquiryEntityRepository;
import com.back2basics.adapter.persistence.inquiry.InquiryMapper;
import com.back2basics.infra.exception.inquiry.InquiryErrorCode;
import com.back2basics.infra.exception.inquiry.InquiryException;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.out.DeleteInquiryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteInquiryJpaAdapter implements DeleteInquiryPort {

    private final InquiryMapper inquiryMapper;
    private final InquiryEntityRepository inquiryEntityRepository;

    public Inquiry softDelete(Inquiry inquiry) {
        InquiryEntity inquiryEntity = inquiryEntityRepository.findById(inquiry.getId())
            .orElseThrow(() -> new InquiryException(InquiryErrorCode.INQUIRY_NOT_FOUND));

        inquiryEntity.markDeleted();
        inquiryEntityRepository.save(inquiryEntity);

        return inquiryMapper.toDomain(inquiryEntityRepository.save(inquiryEntity));
    }

}
