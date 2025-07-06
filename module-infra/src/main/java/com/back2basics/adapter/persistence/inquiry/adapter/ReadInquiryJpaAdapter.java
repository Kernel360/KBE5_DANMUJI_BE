package com.back2basics.adapter.persistence.inquiry.adapter;

import com.back2basics.adapter.persistence.inquiry.InquiryEntityRepository;
import com.back2basics.adapter.persistence.inquiry.InquiryMapper;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.model.InquiryCountsDto;
import com.back2basics.inquiry.port.out.ReadInquiryPort;
import java.util.List;
import java.util.Optional;
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
    public Optional<Inquiry> getInquiry(Long id) {
        return inquiryEntityRepository.findByIdAndDeletedAtIsNull(id).map(inquiryMapper::toDomain);
    }

    @Override
    public List<Inquiry> getAllInquiries() {
        return inquiryEntityRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc()
            .stream().map(inquiryMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Page<Inquiry> getAllInquiries(Pageable pageable) {
        return inquiryEntityRepository.findAllByDeletedAtIsNull(pageable)
            .map(inquiryMapper::toDomain);
    }

    @Override
    public Page<Inquiry> getMyInquiries(Pageable pageable, Long id) {
        return inquiryEntityRepository.findByAuthorIdAndDeletedAtIsNull(id, pageable)
            .map(inquiryMapper::toDomain);
    }

    @Override
    public InquiryCountsDto getInquiryCounts() {
        return inquiryEntityRepository.getInquiryCounts();
    }

    @Override
    public List<Inquiry> getRecentInquiries() {
        return inquiryEntityRepository.findTop5ByDeletedAtIsNullOrderByCreatedAtDesc()
            .stream().map(inquiryMapper::toDomain).toList();
    }

}
