package com.back2basics.inquiry.service;

import com.back2basics.infra.validator.InquiryValidator;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.model.InquiryCountsDto;
import com.back2basics.inquiry.port.in.ReadInquiryUseCase;
import com.back2basics.inquiry.port.out.ReadInquiryPort;
import com.back2basics.inquiry.service.result.ReadInquiryResult;
import com.back2basics.inquiry.service.result.ReadRecentInquiryResult;
import com.back2basics.user.port.in.UserQueryUseCase;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadInquiryService implements ReadInquiryUseCase {

    private final ReadInquiryPort readInquiryPort;
    private final InquiryValidator inquiryValidator;
    private final UserQueryUseCase userQueryUseCase;

    @Override
    public ReadInquiryResult getInquiry(Long id) {
        Inquiry inquiry = inquiryValidator.findInquiry(id);
        Long authorId = inquiry.getAuthorId();

        String authorName = userQueryUseCase.getNameById(authorId);

        return ReadInquiryResult.toResult(inquiry, authorName);
    }

    @Override
    public List<ReadInquiryResult> getAllInquiries() {
        List<Inquiry> inquiries = readInquiryPort.getAllInquiries();
        List<Long> authorIds = inquiries.stream()
            .map(Inquiry::getAuthorId)
            .toList();

        Map<Long, String> authorNameMap = userQueryUseCase.getNameByIds(authorIds);

        return inquiries.stream()
            .map(inquiry -> ReadInquiryResult.toResult(inquiry,
                authorNameMap.getOrDefault(inquiry.getAuthorId(), "알 수 없음")
            ))
            .toList();
    }

    @Override
    public Page<ReadInquiryResult> getAllInquiries(Pageable pageable) {
        Page<Inquiry> inquiryPage = readInquiryPort.getAllInquiries(pageable);

        List<Long> authorIds = inquiryPage.getContent().stream()
            .map(Inquiry::getAuthorId)
            .toList();

        Map<Long, String> authorNameMap = userQueryUseCase.getNameByIds(authorIds);

        List<ReadInquiryResult> results = inquiryPage.getContent().stream()
            .map(inquiry -> ReadInquiryResult.toResult(
                inquiry,
                authorNameMap.getOrDefault(inquiry.getAuthorId(), "알 수 없음")
            ))
            .toList();

        return new PageImpl<>(results, pageable, inquiryPage.getTotalElements());
    }

    @Override
    public Page<ReadInquiryResult> getMyInquiries(Pageable pageable, Long id) {
        Page<Inquiry> inquiryPage = readInquiryPort.getMyInquiries(pageable, id);

        List<Long> authorIds = inquiryPage.getContent().stream()
            .map(Inquiry::getAuthorId)
            .toList();

        Map<Long, String> authorNameMap = userQueryUseCase.getNameByIds(authorIds);

        List<ReadInquiryResult> results = inquiryPage.getContent().stream()
            .map(inquiry -> ReadInquiryResult.toResult(
                inquiry,
                authorNameMap.getOrDefault(inquiry.getAuthorId(), "알 수 없음")
            ))
            .toList();

        return new PageImpl<>(results, pageable, inquiryPage.getTotalElements());
    }

    @Override
    public InquiryCountsDto getInquiryCounts() {
        return readInquiryPort.getInquiryCounts();
    }

    @Override
    public List<ReadRecentInquiryResult> getRecentInquiries() {
        return readInquiryPort.getRecentInquiries()
            .stream().map(ReadRecentInquiryResult::toResult).toList();
    }

}
