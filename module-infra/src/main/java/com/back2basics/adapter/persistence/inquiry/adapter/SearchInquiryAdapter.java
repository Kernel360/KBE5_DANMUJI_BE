package com.back2basics.adapter.persistence.inquiry.adapter;

import static com.back2basics.adapter.persistence.inquiry.QInquiryEntity.inquiryEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.inquiry.InquiryMapper;
import com.back2basics.adapter.persistence.inquiry.InquirySummaryProjection;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.model.InquiryStatus;
import com.back2basics.inquiry.port.in.command.InquirySearchCommand;
import com.back2basics.inquiry.port.out.SearchInquiryPort;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchInquiryAdapter implements SearchInquiryPort {

    private final JPAQueryFactory queryFactory;
    private final InquiryMapper inquiryMapper;


    @Override
    public Page<Inquiry> searchWithFilter(InquirySearchCommand command, Pageable pageable) {

        List<InquirySummaryProjection> projections = queryFactory
            .select(Projections.constructor(
                InquirySummaryProjection.class,
                inquiryEntity.id,
                inquiryEntity.authorId,
                userEntity.name,
                userEntity.username,
                userEntity.role,
                inquiryEntity.title,
                inquiryEntity.inquiryStatus,
                inquiryEntity.createdAt
            ))
            .from(inquiryEntity)
            .join(userEntity).on(inquiryEntity.authorId.eq(userEntity.id))
            .where(filterOptions(command).and(inquiryEntity.deletedAt.isNull()))
            .orderBy(inquiryEntity.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<Inquiry> content = projections.stream()
            .map(inquiryMapper::toDomain)
            .collect(Collectors.toList());

        Long count = queryFactory
            .select(inquiryEntity.count())
            .from(inquiryEntity)
            .where(filterOptions(command).and(inquiryEntity.deletedAt.isNull()))
            .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, () -> count != null ? count : 0);
    }

    private BooleanExpression filterOptions(InquirySearchCommand command) {
        return isActive()
            .and(matchesTitle(command.getTitle()))
            .and(matchesStatus(command.getStatus()))
            .and(matchesStartDate(command.getStartDate()))
            .and(matchesEndDate(command.getEndDate()))
            .and(matchesAuthorUsername(command.getAuthorUsername()))
            .and(matchesAuthorName(command.getAuthorName()));
    }

    private BooleanExpression isActive() {
        return inquiryEntity.deletedAt.isNull();
    }

    private BooleanExpression matchesTitle(String title) {
        return (title == null || title.isBlank()) ? null : inquiryEntity.title.containsIgnoreCase(title);
    }

    private BooleanExpression matchesStatus(InquiryStatus status) {
        return (status == null) ? null : inquiryEntity.inquiryStatus.eq(status);
    }

    private BooleanExpression matchesStartDate(LocalDate startDate) {
        return (startDate == null) ? null : inquiryEntity.createdAt.goe(startDate.atStartOfDay());
    }

    private BooleanExpression matchesEndDate(LocalDate endDate) {
        return (endDate == null) ? null : inquiryEntity.createdAt.loe(endDate.atTime(23, 59, 59));
    }

    private BooleanExpression matchesAuthorUsername(String username) {
        return (username == null || username.isBlank()) ? null : userEntity.username.containsIgnoreCase(username);
    }

    private BooleanExpression matchesAuthorName(String name) {
        return (name == null || name.isBlank()) ? null : userEntity.name.containsIgnoreCase(name);
    }
}
