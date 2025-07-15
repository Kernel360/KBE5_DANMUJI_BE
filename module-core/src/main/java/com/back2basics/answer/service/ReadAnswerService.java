package com.back2basics.answer.service;

import com.back2basics.answer.model.Answer;
import com.back2basics.answer.port.in.ReadAnswerUseCase;
import com.back2basics.answer.port.out.ReadAnswerPort;
import com.back2basics.answer.service.result.ReadAnswerResult;
import com.back2basics.infra.validator.InquiryValidator;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.port.out.UserQueryPort;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadAnswerService implements ReadAnswerUseCase {

    private final InquiryValidator inquiryValidator;
    private final ReadAnswerPort readAnswerPort;
    private final UserQueryUseCase userQueryUseCase;
    private final UserQueryPort userQueryPort;

    @Override
    public Page<ReadAnswerResult> getAnswersByInquiryId(Long inquiryId,
        Pageable pageable) {
        Page<Answer> answers = readAnswerPort.findAllByInquiryIdAndDeletedAtIsNull(inquiryId,
            pageable);
        List<Long> authorIds = answers.getContent().stream().map(Answer::getAuthorId).toList();

        Map<Long, String> authorNameMap = userQueryPort.getNameByIds(authorIds);

        List<ReadAnswerResult> results = answers.getContent().stream()
            .map(answer -> ReadAnswerResult.toResult(
                answer,
                authorNameMap.getOrDefault(answer.getAuthorId(), "알 수 없음")
            ))
            .toList();

        return new PageImpl<>(results, pageable, answers.getTotalElements());
    }

}
