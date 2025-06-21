package com.back2basics.approval.port.in.command;

import java.util.List;

public record CreateApprovalCommand(List<Long> responseIds) {

}
