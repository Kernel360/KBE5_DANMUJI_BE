package com.back2basics.checklist.port.in.command;

import java.util.List;

public record CreateChecklistCommand(String title, String content, List<Long> responseIds) {

}
