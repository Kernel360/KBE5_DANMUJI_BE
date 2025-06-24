package com.back2basics.history.model;

import com.back2basics.user.model.Role;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;

@Getter
public class History {

    private final HistoryType historyType;
    private final DomainType domainType;
    private final Long domainId;
    private final LocalDateTime changedAt;
    private final String changerId;
    private final String changerName;
    private final String changerUsername;
    private final Role changerRole;
    private final Map<String, Object> before;
    private final Map<String, Object> after;
    private final LocalDateTime createdAt;
    private final String message;

    public History(HistoryType historyType, DomainType domainType, Long domainId,
        LocalDateTime changedAt, String changerId, String changerName, String changerUsername,
        Role changerRole,
        Map<String, Object> before, Map<String, Object> after,
        LocalDateTime createdAt, String message) {

        this.historyType = historyType;
        this.domainType = domainType;
        this.domainId = domainId;
        this.changedAt = changedAt;
        this.changerId = changerId;
        this.changerName = changerName;
        this.changerUsername = changerUsername;
        this.changerRole = changerRole;
        this.before = before;
        this.after = after;
        this.createdAt = createdAt;
        this.message = message;
    }

    public static History create(HistoryType historyType, DomainType domainType, Long domainId,
        String changerId, String changerName, String changerUsername, Role changerRole,
        Map<String, Object> before, Map<String, Object> after, LocalDateTime createdAt,
        String message) {
        return new History(
            historyType,
            domainType,
            domainId,
            LocalDateTime.now(),
            changerId,
            changerName,
            changerUsername,
            changerRole,
            before,
            after,
            createdAt, message);
    }

}
