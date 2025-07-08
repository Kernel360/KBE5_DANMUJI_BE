package com.back2basics.inquiry.service;

import com.back2basics.infra.validator.InquiryValidator;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.in.ReadInquiryUseCase;
import com.back2basics.inquiry.port.in.command.InquirySearchCommand;
import com.back2basics.inquiry.port.out.ReadInquiryPort;
import com.back2basics.inquiry.service.result.CountInquiryResult;
import com.back2basics.inquiry.service.result.ReadInquiryResult;
import com.back2basics.inquiry.service.result.ReadRecentInquiryResult;
import com.back2basics.user.port.in.UserQueryUseCase;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public CountInquiryResult getInquiryCounts() {
        return readInquiryPort.getInquiryCounts();
    }

    @Override
    public List<ReadRecentInquiryResult> getRecentInquiries() {
        return readInquiryPort.getRecentInquiries()
            .stream().map(ReadRecentInquiryResult::toResult).toList();
    }

    @Override
    public Page<ReadInquiryResult> searchInquiries(InquirySearchCommand command,
        Pageable pageable) {
        // 1) authorName → authorId 변환
        InquirySearchCommand searchCmd = command;   // 기본값
        if (StringUtils.hasText(command.getAuthorName())) {
            Optional<Long> authorIdOpt = userQueryUseCase.getIdByName(command.getAuthorName());
            if (authorIdOpt.isEmpty()) {
                return Page.empty(pageable);
            }

            // 🔸 불변 객체 → 복사‑수정
            searchCmd = command.toBuilder()
                .authorId(authorIdOpt.get())
                .build();
        }

        // 2) 조회
        Page<Inquiry> page = readInquiryPort.search(searchCmd, pageable);

        // 3) 결과 매핑
        if (StringUtils.hasText(command.getAuthorName())) {
            return page.map(inq -> ReadInquiryResult.toResult(inq, command.getAuthorName()));
        } else {
            Map<Long, String> idNameMap = userQueryUseCase.getNameByIds(
                page.getContent().stream()
                    .map(Inquiry::getAuthorId)
                    .distinct()
                    .toList()
            );
            return page.map(inq -> ReadInquiryResult.toResult(
                inq,
                idNameMap.getOrDefault(inq.getAuthorId(), "알 수 없음")
            ));
        }
    }
}
