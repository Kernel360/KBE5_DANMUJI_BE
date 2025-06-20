package com.back2basics.answer.port.out;

import com.back2basics.answer.model.Answer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadAnswerPort {

    Optional<Answer> findById(Long answerId);

    Page<Answer> findAllByInquiryIdAndDeletedAtIsNull(Long inquiryId, Pageable pageable);
}
