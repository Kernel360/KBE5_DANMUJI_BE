package com.back2basics.domain.history.dto.response;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;
import com.back2basics.history.service.result.HistorySimpleResult;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;

public record HistorySimpleResponse(
    String id,
    HistoryType historyType,
    DomainType domainType,
    Long domainId,
    LocalDateTime changedAt,
    String changerId,
    String changerName,
    Role changerRole,
    String changerIp
) {

    public static HistorySimpleResponse toResponse(HistorySimpleResult result) {
        return new HistorySimpleResponse(
            result.id(),
            result.historyType(),
            result.domainType(),
            result.domainId(),
            result.changedAt(),
            result.changerId(),
            result.changerName(),
            result.changerRole(),
            result.changerIp()
        );
    }
}