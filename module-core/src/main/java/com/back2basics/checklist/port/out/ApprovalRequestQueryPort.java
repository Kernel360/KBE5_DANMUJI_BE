package com.back2basics.checklist.port.out;

import com.back2basics.checklist.model.Checklist;
import java.util.List;

public interface ApprovalRequestQueryPort {

    Checklist findById(Long requestId);

    List<Checklist> findAll();
}
