package com.back2basics.checklist.port.in;

import com.back2basics.checklist.service.result.CheckListResult;
import java.util.List;

public interface GetCheckListUseCase {

    List<CheckListResult> findByUserId(Long userId);

    List<CheckListResult> findByPostId(Long postId, Long userId);

    List<CheckListResult> findByToday(Long userId);
}
