package com.back2basics.adapter.persistence.inquiry.adapter;

import com.back2basics.adapter.persistence.inquiry.InquiryEntity;
import com.back2basics.adapter.persistence.inquiry.InquiryEntityRepository;
import com.back2basics.adapter.persistence.inquiry.InquiryMapper;
import com.back2basics.adapter.persistence.inquiry.InquirySpecifications;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.in.command.InquirySearchCommand;
import com.back2basics.inquiry.port.out.ReadInquiryPort;
import com.back2basics.inquiry.service.result.CountInquiryResult;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadInquiryJpaAdapter implements ReadInquiryPort {

    private final InquiryEntityRepository inquiryEntityRepository;
    private final InquiryMapper inquiryMapper;

    @Override
    public Optional<Inquiry> getInquiry(Long id, Long userId) {
        return inquiryEntityRepository.findByIdAndAuthorIdAndDeletedAtIsNull(id, userId)
            .map(inquiryMapper::toDomain);
    }

    @Override
    public Optional<Inquiry> getUsersInquiry(Long id) {
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
    public CountInquiryResult getInquiryCounts() {
        return inquiryEntityRepository.getInquiryCounts();
    }

    @Override
    public List<Inquiry> getRecentInquiries() {
        return inquiryEntityRepository.findTop5ByDeletedAtIsNullOrderByCreatedAtDesc()
            .stream().map(inquiryMapper::toDomain).toList();
    }

    @Override
    public Page<Inquiry> search(InquirySearchCommand condition, Pageable pageable) {
        Specification<InquiryEntity> spec = Specification.where(null);

        spec = spec.and(InquirySpecifications.hasTitle(condition.getTitle()));
        spec = spec.and(InquirySpecifications.hasAuthorId(condition.getAuthorId()));
        spec = spec.and(InquirySpecifications.hasStatus(condition.getStatus()));
        spec = spec.and(InquirySpecifications.betweenCreatedAt(condition.getStartDate(),
            condition.getEndDate()));

        Page<InquiryEntity> entityPage = inquiryEntityRepository.findAll(spec, pageable);

        // Entity → Domain 변환
        return entityPage.map(inquiryMapper::toDomain);
    }
}
