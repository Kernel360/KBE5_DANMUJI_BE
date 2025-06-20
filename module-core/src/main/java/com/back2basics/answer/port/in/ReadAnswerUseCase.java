package com.back2basics.answer.port.in;

import com.back2basics.answer.service.result.ReadAnswerResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadAnswerUseCase {

    Page<ReadAnswerResult> getAnswersByInquiryId(Long inquiryId, Pageable pageable);
}
