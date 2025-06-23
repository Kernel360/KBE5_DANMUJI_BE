package com.back2basics.history.model;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;

@Getter
public class History {

    private final HistoryType historyType;
    private final DomainType domainType;
    private final Long domainId;
    private final LocalDateTime changedAt;
    private final String changedBy;
    private final Map<String, Object> before;
    private final Map<String, Object> after;
    private final LocalDateTime createdAt;

    public History(HistoryType historyType, DomainType domainType, Long domainId,
        LocalDateTime changedAt,
        String changedBy, Map<String, Object> before, Map<String, Object> after,
        LocalDateTime createdAt) {

        this.historyType = historyType;
        this.domainType = domainType;
        this.domainId = domainId;
        this.changedAt = changedAt;
        this.changedBy = changedBy;
        this.before = before;
        this.after = after;
        this.createdAt = createdAt;
    }

    public static History create(HistoryType historyType, DomainType domainType, Long domainId,
        String changedBy,
        Map<String, Object> before, Map<String, Object> after, LocalDateTime createdAt) {
        return new History(
            historyType,
            domainType,
            domainId,
            LocalDateTime.now(),
            changedBy,
            before,
            after,
            createdAt);
    }

}
