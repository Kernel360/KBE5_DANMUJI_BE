package com.back2basics.checklist.port.out;

import com.back2basics.checklist.model.CheckList;

public interface CheckListCommandPort {

    void save(CheckList checkList);

    void delete(Long checkListId);
}
