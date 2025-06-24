package com.back2basics.domain.history.dto.response;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;
import com.back2basics.history.service.result.HistoryDetailResult;
import java.time.LocalDateTime;
import java.util.Map;

public record HistoryDetailResponse(
    String id,
    HistoryType historyType,
    DomainType domainType,
    Long domainId,
    LocalDateTime changedAt,
    String changedBy,
    Map<String, Object> before,
    Map<String, Object> after,
    LocalDateTime createdAt
) {

    public static HistoryDetailResponse from(HistoryDetailResult result) {
        return new HistoryDetailResponse(
            result.id(),
            result.historyType(),
            result.domainType(),
            result.domainId(),
            result.changedAt(),
            result.changedBy(),
            result.before(),
            result.after(),
            result.createdAt()
        );
    }
}
