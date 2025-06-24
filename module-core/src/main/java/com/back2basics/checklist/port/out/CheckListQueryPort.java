package com.back2basics.checklist.port.out;

import com.back2basics.checklist.model.CheckList;

public interface CheckListQueryPort {

    CheckList findById(Long id);
}
