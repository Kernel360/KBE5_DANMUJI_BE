package com.back2basics.domain.history.dto.request;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;
import com.back2basics.history.port.in.command.HistorySearchCommand;
import jakarta.annotation.Nullable;
import java.time.LocalDateTime;

public record HistorySearchRequest(
    @Nullable HistoryType historyType,
    @Nullable DomainType domainType,
    @Nullable String changedBy,
    @Nullable LocalDateTime changedFrom,
    @Nullable LocalDateTime changedTo

) {

    public HistorySearchCommand toCommand() {
        return new HistorySearchCommand(historyType, domainType, changedBy, changedFrom,
            changedTo);
    }
}