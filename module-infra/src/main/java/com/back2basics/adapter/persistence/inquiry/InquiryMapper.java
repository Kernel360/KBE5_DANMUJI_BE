package com.back2basics.adapter.persistence.inquiry;

import com.back2basics.inquiry.model.Inquiry;
import org.springframework.stereotype.Component;

@Component
public class InquiryMapper {

    public Inquiry toDomain(InquiryEntity entity) {
        if (entity == null) {
            return null;
        }

        return Inquiry.builder()
            .id(entity.getId())
            .authorId(entity.getAuthorId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .inquiryStatus(entity.getInquiryStatus())
            .createdAt(entity.getCreatedAt())
            .build();
    }

    public InquiryEntity toEntity(Inquiry domain) {
        if (domain == null) {
            return null;
        }

        return InquiryEntity.builder()
            .id(domain.getId())
            .authorId(domain.getAuthorId())
            .title(domain.getTitle())
            .content(domain.getContent())
            .inquiryStatus(domain.getInquiryStatus())
            .build();
    }

    public Inquiry toDomain(InquirySummaryProjection projection) {
        if (projection == null) {
            return null;
        }

        return Inquiry.builder()
            .id(projection.inquiryId())
            .authorId(projection.authorId())
            .name(projection.authorName())
            .username(projection.authorUsername())
            .role(projection.authorRole())
            .title(projection.title())
            .inquiryStatus(projection.status())
            .createdAt(projection.createdAt())
            .build();
    }

}
