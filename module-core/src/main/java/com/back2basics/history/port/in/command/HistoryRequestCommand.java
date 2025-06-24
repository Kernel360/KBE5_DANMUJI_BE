package com.back2basics.history.port.in.command;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;

public record HistoryRequestCommand(
    HistoryType historyType,
    DomainType domainType,
    Long domainId,
    Long changedBy,
    Object before,
    Object after
) {

}