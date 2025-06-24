package com.back2basics.history.service.result;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;
import java.time.LocalDateTime;
import java.util.Map;

public record HistoryDetailResult(
    String id,
    HistoryType historyType,
    DomainType domainType,
    Long domainId,
    LocalDateTime changedAt,
    Long changedBy,
    Map<String, Object> before,
    Map<String, Object> after,
    LocalDateTime createdAt
) {

}