package com.back2basics.adapter.persistence.inquiry;

import com.back2basics.inquiry.model.InquiryStatus;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public class InquirySpecifications {

    public static Specification<InquiryEntity> hasTitle(String title) {
        return (root, query, cb) ->
            title == null ? null : cb.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<InquiryEntity> hasAuthorId(Long authorId) {
        return (root, query, cb) -> {
            if (authorId == null) {
                return null; // 조건 무시
            }
            return cb.equal(root.get("authorId"), authorId);
        };
    }

    public static Specification<InquiryEntity> hasStatus(InquiryStatus status) {
        return (root, query, cb) ->
            status == null ? null : cb.equal(root.get("inquiryStatus"), status);
    }

    public static Specification<InquiryEntity> betweenCreatedAt(LocalDate startDate,
        LocalDate endDate) {
        return (root, query, cb) -> {
            if (startDate != null && endDate != null) {
                return cb.between(root.get("createdAt"),
                    startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
            } else if (startDate != null) {
                return cb.greaterThanOrEqualTo(root.get("createdAt"), startDate.atStartOfDay());
            } else if (endDate != null) {
                return cb.lessThanOrEqualTo(root.get("createdAt"), endDate.atTime(23, 59, 59));
            }
            return null;
        };
    }

}
