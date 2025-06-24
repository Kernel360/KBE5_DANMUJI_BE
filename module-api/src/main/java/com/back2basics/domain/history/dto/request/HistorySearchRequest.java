package com.back2basics.domain.history.dto.request;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;

public record HistorySearchRequest(
    HistoryType historyType,
    DomainType domainType,
    String domainId,
    String changedBy
) {

    public HistorySearchCommand toCommand() {
        return new HistorySearchCommand(historyType, domainType, domainId, changedBy);
    }
}