package com.back2basics.checklist.port.out;

import com.back2basics.checklist.model.CheckList;
import java.util.List;

public interface CheckListQueryPort {

    CheckList findById(Long id);

    List<CheckList> findByUserId(Long userId);

    List<CheckList> findByPostId(Long postId, Long userId);
}
