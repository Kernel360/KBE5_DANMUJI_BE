package com.back2basics.domain.history.dto.response;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;
import com.back2basics.history.service.result.HistorySimpleResult;
import java.time.LocalDateTime;

public record HistorySimpleResponse(
    String id,
    HistoryType historyType,
    DomainType domainType,
    Long domainId,
    LocalDateTime changedAt,
    String changedBy
) {

    public static HistorySimpleResponse toResponse(HistorySimpleResult result) {
        return new HistorySimpleResponse(
            result.id(),
            result.historyType(),
            result.domainType(),
            result.domainId(),
            result.changedAt(),
            result.changedBy()
        );
    }
}