package com.back2basics.domain.history.dto.response;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;
import com.back2basics.history.service.result.HistoryDetailResult;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;
import java.util.Map;

public record HistoryDetailResponse(
    String id,
    HistoryType historyType,
    DomainType domainType,
    Long domainId,
    LocalDateTime changedAt,
    String changerId,
    String changerName,
    String changerUsername,
    Role changerRole,
    Map<String, Object> before,
    Map<String, Object> after,
    LocalDateTime createdAt,
    String message
) {

    public static HistoryDetailResponse toResponse(HistoryDetailResult result) {
        return new HistoryDetailResponse(
            result.id(),
            result.historyType(),
            result.domainType(),
            result.domainId(),
            result.changedAt(),
            result.changerId(),
            result.changerName(),
            result.changerUsername(),
            result.changerRole(),
            result.before(),
            result.after(),
            result.createdAt(),
            result.message()
        );
    }
}
