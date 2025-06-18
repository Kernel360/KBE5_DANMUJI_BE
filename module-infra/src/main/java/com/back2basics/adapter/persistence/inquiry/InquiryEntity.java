package com.back2basics.adapter.persistence.inquiry;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.inquiry.model.InquiryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "inquiries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private InquiryStatus inquiryStatus;

    @Builder
    public InquiryEntity(Long id, Long authorId, String title, String content,
        InquiryStatus inquiryStatus) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.inquiryStatus = inquiryStatus;
    }
}
