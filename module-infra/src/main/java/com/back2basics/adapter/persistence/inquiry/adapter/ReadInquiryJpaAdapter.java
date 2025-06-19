package com.back2basics.adapter.persistence.inquiry.adapter;

import com.back2basics.adapter.persistence.inquiry.InquiryEntityRepository;
import com.back2basics.adapter.persistence.inquiry.InquiryMapper;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.out.ReadInquiryPort;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadInquiryJpaAdapter implements ReadInquiryPort {

    private final InquiryEntityRepository inquiryEntityRepository;
    private final InquiryMapper inquiryMapper;

    @Override
    public Inquiry getInquiry(Long id) {
        return inquiryEntityRepository.findById(id).map(inquiryMapper::toDomain)
            .orElseThrow(() -> new NoSuchElementException("해당하는 문의사항이 없습니다."));
    }

    @Override
    public List<Inquiry> getAllInquiries() {
        return inquiryEntityRepository.findAll()
            .stream().map(inquiryMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Page<Inquiry> getAllInquiries(Pageable pageable) {
        return inquiryEntityRepository.findByDeletedAtIsNull(pageable)
            .map(inquiryMapper::toDomain);
    }

}
