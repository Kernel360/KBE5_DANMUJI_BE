package com.back2basics.history.service.result;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;

public record HistorySimpleResult(
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

}