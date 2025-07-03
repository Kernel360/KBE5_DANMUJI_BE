package com.back2basics.checklist.port.out;

import com.back2basics.checklist.model.Checklist;

public interface ApprovalRequestCommandPort {

    Checklist create(Checklist checklist);

    void update(Checklist checklist);

    void save(Checklist checklist);
}
