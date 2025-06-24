package com.back2basics.domain.history.dto.request;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;
import com.back2basics.history.port.in.command.HistorySearchCommand;

public record HistorySearchRequest(
    HistoryType historyType,
    DomainType domainType,
    Long domainId,
    Long changedBy
) {

    public HistorySearchCommand toCommand() {
        return new HistorySearchCommand(historyType, domainType, domainId, changedBy);
    }
}