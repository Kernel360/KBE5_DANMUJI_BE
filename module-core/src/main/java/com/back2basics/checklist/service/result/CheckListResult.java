package com.back2basics.checklist.service.result;

import com.back2basics.checklist.model.CheckList;
import java.time.LocalDateTime;

public record CheckListResult(Long id, String content, Long postId, LocalDateTime createdAt,
                              boolean isChecked, LocalDateTime checkedAt) {

    public static CheckListResult from(CheckList checkList) {
        return new CheckListResult(
            checkList.getId(),
            checkList.getContent(),
            checkList.getPostId(),
            checkList.getCreatedAt(),
            checkList.getIsChecked(),
            checkList.getCheckedAt()
        );
    }
}
