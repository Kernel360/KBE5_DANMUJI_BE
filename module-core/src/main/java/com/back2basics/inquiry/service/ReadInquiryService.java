package com.back2basics.inquiry.service;

import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.in.ReadInquiryUseCase;
import com.back2basics.inquiry.port.out.ReadInquiryPort;
import com.back2basics.inquiry.service.result.ReadInquiryResult;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.port.out.UserQueryPort;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadInquiryService implements ReadInquiryUseCase {

    private final ReadInquiryPort readInquiryPort;
    private final UserQueryPort userQueryPort;
    private final UserQueryUseCase userQueryUseCase;

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

}
