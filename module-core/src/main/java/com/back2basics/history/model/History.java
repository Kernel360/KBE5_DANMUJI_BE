package com.back2basics.history.model;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;

@Getter
public class History {

    private final String domainType;
    private final Long domainId;
    private final LocalDateTime changedAt;
    private final String changedBy;
    private final Map<String, Object> before;
    private final Map<String, Object> after;
    private final LocalDateTime createdAt;

    public History(String domainType, Long domainId, LocalDateTime changedAt,
        String changedBy, Map<String, Object> before, Map<String, Object> after,
        LocalDateTime createdAt) {

        this.domainType = domainType;
        this.domainId = domainId;
        this.changedAt = changedAt;
        this.changedBy = changedBy;
        this.before = before;
        this.after = after;
        this.createdAt = createdAt;
    }

    public static History create(String domainType, Long domainId, String changedBy,
        Map<String, Object> before, Map<String, Object> after, LocalDateTime createdAt) {
        return new History(
            domainType,
            domainId,
            LocalDateTime.now(),
            changedBy,
            before,
            after,
            createdAt);
    }

}
