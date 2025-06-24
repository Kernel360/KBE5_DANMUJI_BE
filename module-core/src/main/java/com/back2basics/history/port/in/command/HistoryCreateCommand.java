package com.back2basics.history.port.in.command;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;
import com.back2basics.user.model.Role;

public record HistoryCreateCommand(
    HistoryType historyType,
    DomainType domainType,
    Long domainId,
    Long changerId,
    String changerName,
    String changerUsername,
    Role changerRole,
    Object before,
    Object after
) {

}