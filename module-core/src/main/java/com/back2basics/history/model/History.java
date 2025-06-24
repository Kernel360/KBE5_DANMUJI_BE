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
    private final Role changerRole;
    private final String changerIp;
    private final Map<String, Object> before;
    private final Map<String, Object> after;
    private final LocalDateTime createdAt;

    public History(HistoryType historyType, DomainType domainType, Long domainId,
        LocalDateTime changedAt, String changerId, String changerName, Role changerRole,
        String changerIp,
        Map<String, Object> before, Map<String, Object> after,
        LocalDateTime createdAt) {

        this.historyType = historyType;
        this.domainType = domainType;
        this.domainId = domainId;
        this.changedAt = changedAt;
        this.changerId = changerId;
        this.changerName = changerName;
        this.changerRole = changerRole;
        this.changerIp = changerIp;
        this.before = before;
        this.after = after;
        this.createdAt = createdAt;
    }

    public static History create(HistoryType historyType, DomainType domainType, Long domainId,
        String changerId, String changerName, Role changerRole, String changerIp,
        Map<String, Object> before, Map<String, Object> after, LocalDateTime createdAt) {
        return new History(
            historyType,
            domainType,
            domainId,
            LocalDateTime.now(),
            changerId,
            changerName,
            changerRole,
            changerIp,
            before,
            after,
            createdAt);
    }

}
