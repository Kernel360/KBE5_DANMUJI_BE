package com.back2basics.cleaner;

import com.back2basics.SoftDeletableCleaner;
import com.back2basics.adapter.persistence.answer.AnswerEntityRepository;
import com.back2basics.adapter.persistence.inquiry.InquiryEntityRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InquiryCleaner implements SoftDeletableCleaner {

    private final InquiryEntityRepository repository;
    private final AnswerEntityRepository answerEntityRepository;

    @Override
    public void clean(LocalDateTime threshold) {
        // 1. 삭제 대상 문의 목록 조회
        List<Long> deletedInquiryIds = repository.findIdsByDeletedAtBefore(threshold);

        if (!deletedInquiryIds.isEmpty()) {
            // 2. 해당 문의의 답변 삭제
            answerEntityRepository.deleteAllByInquiryIdIn(deletedInquiryIds);

            // 3. 문의 삭제
            repository.deleteByIdIn(deletedInquiryIds);
        }
    }

    @Override
    public String getName() {
        return "Inquiry";
    }
}
