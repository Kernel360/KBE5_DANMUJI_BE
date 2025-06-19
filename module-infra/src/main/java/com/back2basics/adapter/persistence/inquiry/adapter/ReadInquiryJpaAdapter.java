package com.back2basics.adapter.persistence.inquiry.adapter;

import com.back2basics.adapter.persistence.inquiry.InquiryEntityRepository;
import com.back2basics.adapter.persistence.inquiry.InquiryMapper;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.out.ReadInquiryPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadInquiryJpaAdapter implements ReadInquiryPort {

    private final InquiryEntityRepository inquiryEntityRepository;
    private final InquiryMapper inquiryMapper;

    @Override
    public List<Inquiry> getAllInquiries() {
        return inquiryEntityRepository.findAll()
            .stream().map(inquiryMapper::toDomain).collect(Collectors.toList());
    }

}
