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
import com.back2basics.user.port.out.UserQueryPort;
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
    private final UserQueryPort userQueryPort;

    @Override
    public ReadInquiryResult getInquiry(Long id, Long userId) {
        Inquiry inquiry = inquiryValidator.findInquiry(id, userId);
        Long authorId = inquiry.getAuthorId();

        String authorName = userQueryUseCase.getNameById(authorId);

        return ReadInquiryResult.toResult(inquiry, authorName);
    }

    @Override
    public ReadInquiryResult getInquiryAsAdmin(Long id) {
        Inquiry inquiry = inquiryValidator.findUsersInquiry(id);
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

        Map<Long, String> authorNameMap = userQueryPort.getNameByIds(authorIds);

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

        Map<Long, String> authorNameMap = userQueryPort.getNameByIds(authorIds);

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

        Map<Long, String> authorNameMap = userQueryPort.getNameByIds(authorIds);

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

        InquirySearchCommand searchCmd = command;
        if (StringUtils.hasText(command.getAuthorName())) {
            Optional<Long> authorIdOpt = userQueryUseCase.getIdByName(command.getAuthorName());
            if (authorIdOpt.isEmpty()) {
                return Page.empty(pageable);
            }

            searchCmd = command.toBuilder()
                .authorId(authorIdOpt.get())
                .build();
        }

        Page<Inquiry> page = readInquiryPort.search(searchCmd, pageable);

        if (StringUtils.hasText(command.getAuthorName())) {
            return page.map(inq -> ReadInquiryResult.toResult(inq, command.getAuthorName()));
        } else {
            Map<Long, String> idNameMap = userQueryPort.getNameByIds(
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

    @Override
    public Page<ReadInquiryResult> searchUserInquiries(Long userId, InquirySearchCommand command,
        Pageable pageable) {

        InquirySearchCommand searchCmd = command;
        if (StringUtils.hasText(command.getAuthorName())) {
            Optional<Long> authorIdOpt = userQueryUseCase.getIdByName(command.getAuthorName());
            if (authorIdOpt.isEmpty()) {
                return Page.empty(pageable);
            }

            searchCmd = command.toBuilder()
                .authorId(authorIdOpt.get())
                .build();
        }

        Page<Inquiry> page = readInquiryPort.searchUser(userId, searchCmd, pageable);

        if (StringUtils.hasText(command.getAuthorName())) {
            return page.map(inq -> ReadInquiryResult.toResult(inq, command.getAuthorName()));
        } else {
            Map<Long, String> idNameMap = userQueryPort.getNameByIds(
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