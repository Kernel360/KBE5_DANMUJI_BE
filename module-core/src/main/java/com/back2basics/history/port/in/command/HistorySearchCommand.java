package com.back2basics.history.port.in.command;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryType;
import com.back2basics.user.model.Role;

public record HistorySearchCommand(
    HistoryType historyType,
    DomainType domainType,
    Role userRole,
    String changedBy
) {

}