package com.back2basics.inquiry.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.exception.ForbiddenAccessException;
import com.back2basics.infra.validation.validator.InquiryValidator;
import com.back2basics.inquiry.model.Inquiry;
import com.back2basics.inquiry.port.in.DeleteInquiryUseCase;
import com.back2basics.inquiry.port.out.DeleteInquiryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteInquiryService implements DeleteInquiryUseCase {

    private final DeleteInquiryPort deleteInquiryPort;
    private final InquiryValidator inquiryValidator;
    private final HistoryLogService historyLogService;


    // todo : 의견
    // swlee: 모든 메소드에서 넘어오는 파라미터의 변수 이름이 다 달라서 너무 혼란스럽습니다.
    // 똑같이 CustomUserDetails.getId()로 넘어오는 로그인유저아이디인데 어떤건 그냥id 어떤건 userId,
    // 이름도 다른데 순서까지 일관성이 없어서 너무 헷갈려요 통일하면 좋을 것 같습니다.
    @Override
    public void deleteInquiry(Long inquiryId, Long userId) {

        Inquiry inquiry = inquiryValidator.findInquiry(inquiryId);

        // todo : 커스텀익셉션 사용, Validator로 로직 이동
        if (!inquiry.getAuthorId().equals(userId)) {
            throw new ForbiddenAccessException("자신의 문의만 삭제할 수 있습니다.");
        }

        inquiry.markDeleted();
        Inquiry deletedInquiry = deleteInquiryPort.softDelete(inquiry);

        historyLogService.logDeleted(DomainType.INQUIRY, userId, deletedInquiry, "문의사항 비활성화");
    }

}
